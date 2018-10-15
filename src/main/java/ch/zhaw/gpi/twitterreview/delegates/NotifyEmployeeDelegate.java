package ch.zhaw.gpi.twitterreview.delegates;

import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Implementation des Send Tasks "Mitarbeiter benachrichtigen"
 * @author zhaw-pfaefjas
 */
@Named("notifyEmployeeAdapter")
public class NotifyEmployeeDelegate implements JavaDelegate {

    /**
     * Mockt das Senden einer Benachrichtigung per Mail
     * 
     * 1. Die Prozessvariablen auslesen
     * 2. Die E-Mail-Nachricht zusammenstellen
     * 3. E-Mail in der Konsole ausgeben
     * 
     * @param de  Objekt, welches die Verkünfung zur Process Enigne und aktuellen Execution enthält
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution de) throws Exception {
        // Prozessvariablen auslesen
        String email = (String) de.getVariable("email");
        String tweetContent = (String) de.getVariable("tweetContent");
        String checkResult = (String) de.getVariable("checkResult");
        String checkResultComment = (String) de.getVariable("checkResultComment");
        String mailMainPart = (String)de.getVariable("mailMainPart");
        
        // Die E-Mail-Nachricht zusammenbauen
        String mailHauptteil;
        if(mailMainPart != null) {
            mailHauptteil = mailMainPart;
        } else if(checkResult.equals("rejected")){
            mailHauptteil = "Leider wurde diese Tweet-Anfrage abgelehnt mit " +
                    "folgender Begründung:\n" + checkResultComment;
        } else {
            mailHauptteil = "Dein Tweet wurde geposted. Herzlichen Dank für Deinen Beitrag.";
        }
        
        // Mail-Text zusammenbauen
        String mailBody = "Hallo Mitarbeiter\n\n" + "Du hast folgenden Text zum " +
                "Veröffentlichen als Tweet vorgeschlagen:\n" + tweetContent + "\n\n" +
                mailHauptteil + "\n\n" + "Deine Kommunikationsabteilung";
        
        // Mail in Konsole ausgeben
        System.out.println("########### BEGIN MAIL ##########################");
        System.out.println("############################### Mail-Empfänger: " + email);
        System.out.println(mailBody);
        System.out.println("########### END MAIL ############################");
    }
    
}