QTTTEST0 ; AU - QUIT TYPE TEST ; MAY 4, 2013
 ;;1.0;TESTING;; MAY 4, 2013
 ;
 Q
 ;
SUMXYZ(A,B,C) ;
 N RESULT,T
 F I=1:5:1 D  Q:T  
 . S RESULT=RESULT+^X(A)
 . S T=(^X(A,0)>1)
 G:$D(B(0)) SUMY
 D:$D(B(1)) SUMZ^QTTTEST1(B(1),.RESULT)
 ;
SUMC
 N I S I=0
 F  Q:'$D(C(I))  S RESULT=RESULT+C(I) S I=I+1
 Q
 ;
SUMY
 Q ^X("ADS")
 ;
SUMALL
 N A
 S A=1
 F I=1:3:1 D  Q
 . D THRUERR0^QTTEST1(.A)
 W A
 Q
