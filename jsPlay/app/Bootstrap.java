 
import cn.bran.japid.util.JapidFlags;
import models.Contact;
import play.db.jpa.JPA;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
    	JapidFlags.setLogLevelDebug();
        if(JPA.count(Contact.class) == 0) {
            Fixtures.loadModels("data.yml");
        }
    }
    
}

