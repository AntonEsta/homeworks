package petscardfile.classes.storages;

import lombok.NonNull;
import lombok.SneakyThrows;
import petscardfile.classes.card.CardFile;
import petscardfile.classes.card.PetCard;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class XMLPetExporter implements Exporter, Visitor {

    private List<PetCard> exportSourceList;

    public XMLPetExporter() {
        this.exportSourceList = null;
    }


    public XMLPetExporter(@NonNull List<PetCard> exportSourceList) {
        this.exportSourceList = exportSourceList;
    }

    @SneakyThrows
    private void xmlDocCreate(@NonNull List<PetCard> petCards) {

        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(Files.newOutputStream(Paths.get("export.xml")));

        writer.writeStartDocument("1.0");
        writer.writeStartElement("CardFile");

        for (PetCard petCard : petCards) {
            writer.writeStartElement("Pet");
            writer.writeAttribute("Id", String.valueOf(petCard.getId()));


            writer.writeStartElement("Nickname");
            writer.writeCharacters(petCard.getPet().getNickname());
            writer.writeEndElement();

            writer.writeStartElement("Weight");
            writer.writeCharacters(String.valueOf(petCard.getPet().getWeight()));
            writer.writeEndElement();

            writer.writeStartElement("Owner");
            writer.writeAttribute("Id", String.valueOf(petCard.getOwner().getId()));

            writer.writeStartElement("Name");
            writer.writeCharacters(petCard.getOwner().getPerson().getName());
            writer.writeEndElement();

            if (petCard.getOwner().getPerson().getPatronymic() != null) {
                writer.writeStartElement("Patronymic");
                writer.writeCharacters(petCard.getOwner().getPerson().getPatronymic());
                writer.writeEndElement();
            }

            writer.writeStartElement("Surname");
            writer.writeCharacters(petCard.getOwner().getPerson().getSurname());
            writer.writeEndElement();

            writer.writeStartElement("Age");
            writer.writeCharacters(String.valueOf(petCard.getOwner().getPerson().getAge()));
            writer.writeEndElement();

            writer.writeStartElement("Sex");
            writer.writeCharacters(String.valueOf(petCard.getOwner().getPerson().getSex().toString()));
            writer.writeEndElement();

            writer.writeEndElement();

            writer.writeEndElement();

        }

        writer.writeEndDocument();
        writer.flush();

    }

    @Override
    public void export() {
        xmlDocCreate(exportSourceList);
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
