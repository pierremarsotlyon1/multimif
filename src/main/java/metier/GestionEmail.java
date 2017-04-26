package metier;

import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.MailBuilder;
import net.sargue.mailgun.MailRequestCallback;
import net.sargue.mailgun.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by pierremarsot on 21/11/2016.
 */
public class GestionEmail
{
    private final String From = "multimifgr6lyon1@gmail.com";
    protected static final Logger LOGGER = LoggerFactory.getLogger(GestionEmail.class);
    /*private final String username = "multimifgr6lyon1@gmail.com";
    private final String password = "Multimifgr6lyon1@";*/

    /**
     * Permet d'envoyer un email
     * @param to - Email de l'expediteur
     * @param sujet - Email du recepteur
     * @param message - le message
     * @return - succès de l'envoie
     */
    public boolean send(String to, String sujet, String message) {
       /* try {
            if (to == null || to.isEmpty() || sujet == null || sujet.isEmpty() || message == null || message.isEmpty())
                return false;

            Configuration configuration = new Configuration()
                    .domain("sandboxf5d5da0ce58b46aeb09d2c487efcb733.mailgun.org")
                    .apiKey("key-26cc5b72b99fcacdcf02b89e864eb0fa")
                    .from("Multimif", From);

            MailBuilder.using(configuration)
                    .to(to)
                    .subject(sujet)
                    .text(message)
                    .build()
                    .send();

            return true;
        } catch (Exception e) {
            return false;
        }*/

        try {

            //Construct the data
            String data = "userName=" + URLEncoder.encode("encom.agency@gmail.com", "UTF-8");
            data += "&api_key=" + URLEncoder.encode("c0695f55-ba1d-4b63-9de6-c4e18ca6462e", "UTF-8");
            data += "&from=" + URLEncoder.encode(From, "UTF-8");
            data += "&from_name=" + URLEncoder.encode("multimif", "UTF-8");
            data += "&subject=" + URLEncoder.encode(sujet, "UTF-8");
            data += "&body_html=" + URLEncoder.encode(message, "UTF-8");
            data += "&to=" + URLEncoder.encode(to, "UTF-8");

            //Send data
            URL url = new URL("https://api.elasticemail.com/mailer/send");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            wr.close();
            rd.close();

            return true;
        }

        catch(Exception e) {

            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /*
    Client client = Client.create();
    client.addFilter(new HTTPBasicAuthFilter("api",
                "key-26cc5b72b99fcacdcf02b89e864eb0fa"));
    WebResource webResource =
        client.resource("https://api.mailgun.net/v3/sandboxf5d5da0ce58b46aeb09d2c487efcb733.mailgun.org/messages");
    MultivaluedMapImpl formData = new MultivaluedMapImpl();
    formData.add("from", "Mailgun Sandbox <postmaster@sandboxf5d5da0ce58b46aeb09d2c487efcb733.mailgun.org>");
    formData.add("to", "pierre <encom.agency@gmail.com>");
    formData.add("subject", "Hello pierre");
    formData.add("text", "Congratulations pierre, you just sent an email with Mailgun!  You are truly awesome!  You can see a record of this email in your logs: https://mailgun.com/cp/log .  You can send up to 300 emails/day from this sandbox server.  Next, you should add your own domain so you can send 10,000 emails/month for free.");
    return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                                                post(ClientResponse.class, formData);
     */
}
