package it.polimi.ingsw.AlternativeView;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.io.InputStream;

public class JsonHandler extends InputStream {
    Gson gson = new Gson();

    public void javaToJsonParser(Game game){
        gson.toJson(game.getWizards());
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
