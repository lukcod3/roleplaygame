package lolz.GUI;



import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

/*
 * @author David J. Barnes und Michael KÃ¶lling
 * @version 31.07.2011
 */

public class MusicPlayer
{

    private AdvancedPlayer player;

    public MusicPlayer()
    {
        player = null;
    }


    public void dateiAnspielen(String dateiname)
    {
        try {
            playerVorbereiten(dateiname);
            player.play(1000000);
        }
        catch(JavaLayerException e) {
            meldeProblem(dateiname);
        }
        finally {
            killPlayer();
        }
    }


    public void starteAbspielen(final String dateiname)
    {
        try {
            playerVorbereiten(dateiname);
            Thread playerThread = new Thread() {
                public void run()
                {
                    try {
                        player.play(500000);
                    }
                    catch(JavaLayerException e) {
                        meldeProblem(dateiname);
                    }
                    finally {
                        killPlayer();
                    }
                }
            };
            playerThread.start();
        }
        catch (Exception ex) {
            meldeProblem(dateiname);
        }
    }

    public void stop()
    {
        killPlayer();
    }


    private void playerVorbereiten(String dateiname)
    {
        try {
            InputStream is = gibEingabestream(dateiname);
            player = new AdvancedPlayer(is, erzeugeAudiogeraet());
        }
        catch (IOException e) {
            meldeProblem(dateiname);
            killPlayer();
        }
        catch(JavaLayerException e) {
            meldeProblem(dateiname);
            killPlayer();
        }
    }


    private InputStream gibEingabestream(String dateiname)
            throws IOException
    {
        return new BufferedInputStream(
                new FileInputStream(dateiname));
    }


    private AudioDevice erzeugeAudiogeraet()
            throws JavaLayerException
    {
        return FactoryRegistry.systemRegistry().createAudioDevice();
    }


    private void killPlayer()
    {
        synchronized(this) {
            if(player != null) {
                player.stop();
                player = null;
            }
        }
    }


    private void meldeProblem(String dateiname)
    {
        System.out.println("Es gab ein Problem beim Abspielen von: " + dateiname);
    }

}