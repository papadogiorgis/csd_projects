ΓΙΑ ΝΑ ΤΡΕΞΕΙ ΤΟ ΠΡΟΓΡΑΜΜΑ ΘΑ ΠΡΕΠΕΙ
ΝΑ ΚΑΤΕΒΑΣΕΙΣ ΤΟ javafx-sdk-17.0.13 ΑΠΟ ΤΟ
openjfx.io ΚΑΙ ΝΑ ΤΟ ΑΠΟΘΗΚΕΥΣΕΙΣ
ΕΝΤΟΣ ΤΟΥ ΦΑΚΕΛΟΥ PhaseB_4975/lib.
ΔΕΝ ΤΑ ΕΧΩ ΗΔΗ ΕΚΕΙ ΕΠΕΙΔΗ ΤΟ ELEARN ΔΕ ΜΕ ΑΦΗΝΕ ΝΑ ΑΝΕΒΑΣΩ
ΑΡΧΕΙΟ ΜΕΓΑΛΥΤΕΡΟ ΤΩΝ 50ΜΒ!!!

ΓΙΑ ΝΑ ΤΡΕΞΕΙΣ ΤΟ ΠΡΟΤΖΕΚΤ ΣΤΟ ECLIPSE IDE;
-αφού το ανοίξεις> package explorer>
 right click on "resources" file>
 Build Path> Use as Source Folder
-package explorer> right click on project folder>
 properties>java build path>libraries>
 add external jars>
 select ALL jars from "C:\...\PhaseB_4975\lib\openjfx-17.0.13_windows-x64_bin-sdk\javafx-sdk-17.0.13\lib">
 apply and close

ΟΣΟΝ ΑΦΟΡΑ ΤΟ UML:
-έχει γίνει σε Visual Paradigm
-πήγαινε στο "C:\...\PhaseB_4975\vpproject">
 πάτα το αρχείο PhaseB_4975.vpp
-υπάρχουν ΔΎΟ διαγράμματα. ένα ModelDiagram και ένα ControllerDiagram

ΟΣΟΝ ΑΦΟΡΑ ΤΟ JAR:
-πήγαινε powershell>φάκελο του πρότζεκτ>
τρέξε "java --module-path "C:\...\PhaseB_4975\lib\openjfx-17.0.13_windows-x64_bin-sdk\javafx-sdk-17.0.13\lib" --add-modules javafx.controls,javafx.fxml,javafx.media --add-exports javafx.graphics/com.sun.glass.utils=ALL-UNNAMED --add-exports javafx.media/com.sun.media.jfxmediaimpl=ALL-UNNAMED -jar PhaseB_4975.jar "
>ΑΝΤΙΚΑΤΕΣΤΗΣΕ ΤΑ ΥΠΟΚΟΡΙΣΤΙΚΑ ΜΕ ΤΟ ΥΠΟΛΟΙΠΟ PATH

Παπαδάκης Γεώργιος 4975