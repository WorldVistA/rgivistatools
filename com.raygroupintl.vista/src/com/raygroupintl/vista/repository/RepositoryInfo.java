//---------------------------------------------------------------------------
// Copyright 2012 Ray Group International
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//---------------------------------------------------------------------------

package com.raygroupintl.vista.repository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.raygroupintl.m.parsetree.Routine;
import com.raygroupintl.m.token.TFRoutine;
import com.raygroupintl.m.token.TRoutine;
import com.raygroupintl.parser.SyntaxErrorException;

public class RepositoryInfo {
	private final static Logger LOGGER = Logger.getLogger(RepositoryInfo.class.getName());

	public static class PackageInRepository extends VistaPackage {
		public TFRoutine tokenizer;
		private List<Routine> nodes;

		public PackageInRepository(String packageName, String directoryName, TFRoutine tokenizer) {
			super(packageName, directoryName);
			this.tokenizer = tokenizer;
		}
		
		@Override
		public Path getPath() {
			String vistaFOIARoot = RepositoryInfo.getLocation();
			if (vistaFOIARoot == null) {
				vistaFOIARoot = "";
			}
			String dir = this.getDirectoryName();
			Path path = Paths.get(vistaFOIARoot, "Packages", dir);
			return path;			
		}
		
		//@Override
		public List<Routine> getNodes() {
			//List<Routine> current = super.getCurrentNodes();
			if (this.nodes == null) {
				this.nodes = new ArrayList<Routine>();
				try {
					List<Path> paths = this.getRoutineFilePaths();
					for (Path path : paths) {
						try {
							//LOGGER.info("ROUTINE: " + path.toString());
							TRoutine tr = this.tokenizer.tokenize(path);
							Routine node = tr.getNode();
							this.nodes.add(node);
							//this.add(node);
						} catch (SyntaxErrorException ex) {
							LOGGER.log(Level.SEVERE, "Syntax error found in routine: " + path.toString());
						} 
					}
				} catch (IOException ioex) {
					LOGGER.log(Level.SEVERE, "IO error for package: " + this.getPackageName());
				}				
			}
			return this.nodes;
			//return super.getNodes();
		}
		
		//@Override
		public boolean contains(String routineName) {
			List<String> prefixes = this.getPrefixes();
			for (String prefix : prefixes) {
				if (routineName.startsWith(prefix)) {
					return true;
				}
			}
			return false;
		}		
	}
	
	private List<PackageInRepository> packages = new ArrayList<PackageInRepository>();
	private Map<String, PackageInRepository> packagesByName = new HashMap<String, PackageInRepository>();
	private TreeMap<String, PackageInRepository> packagesByPrefix = new TreeMap<String, PackageInRepository>();
	private TFRoutine tokenizer;	
	
	private RepositoryInfo(TFRoutine tokenizer) {
		this.tokenizer = tokenizer;
	}
	
	private void addPackage(PackageInRepository packageInfo) {
		this.packages.add(packageInfo);
		this.packagesByName.put(packageInfo.getPackageName(), packageInfo);
		for (String prefix : packageInfo.getPrefixes()) {
			this.packagesByPrefix.put(prefix, packageInfo);
		}
	}
	
	public static String getLocation() {
		return System.getenv("VistA-FOIA");
	}
	
	public PackageInRepository getPackage(String packageName) {
		return this.packagesByName.get(packageName);
	}
	
	public List<PackageInRepository> getPackages(List<String> packageNames) {
		List<PackageInRepository> result = new ArrayList<PackageInRepository>(packageNames.size());
		for (String name : packageNames) {
			PackageInRepository p = this.getPackage(name);
			result.add(p);
		}
		return result;
	}
	
	public List<PackageInRepository> getAllPackages() {
		Collection<PackageInRepository> pis = this.packagesByPrefix.values();
		List<PackageInRepository> result = new ArrayList<PackageInRepository>(pis.size());
		for (PackageInRepository pi : pis) {
			result.add(pi);
		}
		return result;
	}
	
	public Path getPackagePath(String packageName) {
		String vistaFOIARoot = RepositoryInfo.getLocation();
		if (vistaFOIARoot == null) {
			vistaFOIARoot = "";
		}
		VistaPackage pi = this.getPackage(packageName);
		String dir = pi.getDirectoryName();
		Path path = Paths.get(vistaFOIARoot, "Packages", dir);
		return path;
	}
	
	public VistaPackage getPackageFromPrefix(String prefix) {
		return this.packagesByPrefix.get(prefix);
	}
	
	public VistaPackage getPackageFromRoutineName(String routineName) {
		VistaPackage result = null;
		int n = routineName.length();
		for (int i=0; i<n; ++i) {
			String prefix = routineName.substring(0, i);
			VistaPackage pi = this.packagesByPrefix.get(prefix);
			if ((pi == null) && (result != null)) {
				return result;
			}
			result = pi;
		}
		if (result == null) {
			result = new PackageInRepository("UNCATEGORIZED", "Uncategorized", this.tokenizer);
		}
		return result;
	}
	
	public static RepositoryInfo getInstance(Scanner scanner, TFRoutine tf) throws IOException {
		RepositoryInfo r = new RepositoryInfo(tf);
		PackageInRepository packageInfo = null;
		scanner.nextLine();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] pieces = line.split(",");
			int n = pieces.length;
			if (pieces[0].length() > 0) {
				packageInfo = new PackageInRepository(pieces[0], pieces[1], tf);
				if ((n > 2) && (pieces[2].length() > 0)) {
					packageInfo.addPrefix(pieces[2]);
				}
				if ((n > 3) && (pieces[3].length() > 0)) {
					packageInfo.addFile(pieces[3], pieces[4]);
				}
				r.addPackage(packageInfo);
			}
		}
		return r;		
	}
	
	public static RepositoryInfo getInstance(String root, TFRoutine tf) throws IOException {
		Path path = Paths.get(root, "Packages.csv");
		Scanner scanner = new Scanner(path);
		RepositoryInfo r = getInstance(scanner, tf);
		scanner.close();
		return r;
	}

	public static RepositoryInfo getInstance(TFRoutine tf) throws IOException {
		String root = RepositoryInfo.getLocation();
		return getInstance(root, tf);
	}
}
