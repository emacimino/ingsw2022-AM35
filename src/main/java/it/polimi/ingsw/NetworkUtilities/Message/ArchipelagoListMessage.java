package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.util.List;

public class ArchipelagoListMessage extends Message{
    private static final long serialVersionUID = 5281586546181603771L;
    private final List<Archipelago> archipelagoList;

    public ArchipelagoListMessage(List<Archipelago> archipelagoList){
        this.archipelagoList = archipelagoList;
    }

    public List<Archipelago> getArchipelagoList() {
        return archipelagoList;
    }
}
