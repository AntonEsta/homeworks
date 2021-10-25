package petscardfile.classes.storages;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import lombok.NonNull;
import lombok.SneakyThrows;
import petscardfile.classes.card.CardFile;
import petscardfile.classes.card.PetCard;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONPetExporter implements Exporter, Visitor {

    private List<PetCard> exportSourceList;

    public JSONPetExporter() {
        this.exportSourceList = null;
    }


    public JSONPetExporter(@NonNull List<PetCard> exportSourceList) {
        this.exportSourceList = exportSourceList;
    }

    @SneakyThrows
    private void jsonDocCreate(@NonNull List<PetCard> petCards) {

        BufferedWriter writer = Files.newBufferedWriter(Paths.get("export.json"));

        try {

            JsonObject petJsonObject = null;

            for (PetCard petCard : petCards) {

                petJsonObject = new JsonObject();
                petJsonObject.put("id", petCard.getId().toString());
                petJsonObject.put("Nickname", petCard.getPet().getNickname());
                petJsonObject.put("Weight", petCard.getPet().getWeight());

                JsonObject ownerJsonObject = new JsonObject();
                ownerJsonObject.put("Id", petCard.getOwner().getId().toString());
                ownerJsonObject.put("Name", petCard.getOwner().getPerson().getName());
                ownerJsonObject.put("Patronymic", petCard.getOwner().getPerson().getPatronymic());
                ownerJsonObject.put("Surname", petCard.getOwner().getPerson().getSurname());
                ownerJsonObject.put("Age", petCard.getOwner().getPerson().getAge());
                ownerJsonObject.put("Sex", petCard.getOwner().getPerson().getSex().toString());

                petJsonObject.put("Owner", ownerJsonObject);

            }

            Jsoner.serialize(petJsonObject, writer);

        } finally {
            writer.close();
        }

    }

    @Override
    public void export() {
        jsonDocCreate(exportSourceList);
    }

    @Override
    public void accept(@NonNull Visitor v) {
        if (v instanceof CardFile) {
            List<PetCard> petCardList = new ArrayList<>();
            for (PetCard petCard : (CardFile) v) {
                petCardList.add(petCard);
            }
            exportSourceList = petCardList;
            export();
        } else {
            throw new ClassCastException("Not support class.");
        }
    }

}
