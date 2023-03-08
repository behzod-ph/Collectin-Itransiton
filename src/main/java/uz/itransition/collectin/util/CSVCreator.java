package uz.itransition.collectin.util;

import com.opencsv.CSVWriter;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.itransition.collectin.entity.Collection;
import uz.itransition.collectin.entity.Field;
import uz.itransition.collectin.entity.FieldValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public final class CSVCreator extends FileUtil {

    private static final Logger log = LoggerFactory.getLogger(CSVCreator.class);

/**
 * @ofCollection() - writes a file of Collection with it items as CSV format and returns absolute path in string;
 **/
    public static String ofCollection(CSVWriter writer,String lang,Collection collection, List<Field> fields, List<FieldValue> filedValues) {
        final String FILE_NAME = String.format("%d.csv",new Date().getTime());
        try {
            List<String[]> data = new ArrayList<>();
            data.addAll(collection.toCSVCollectionHeader(lang));
            data.addAll(List.of(new String[0],new String[]{lang.equals("ENG") ? "Items" : "Элемент"},headCollectionItemFields(fields, lang)));
            data.addAll(valuesCollectionItemFields(filedValues, fields.size()));
            writer.writeAll(data);
            writer.close();
            return FILE_NAME;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Strings.EMPTY;
    }

    ;

    public static String[] headCollectionItemFields(List<Field> fields, String lang) {
        final String[] head = new String[fields.size()+2];
        for (int i = 0; i < fields.size(); i++) {
            head[i] = fields.get(i).getName();
        }
        head[head.length -2] = lang.equals("ENG") ? "likes" : "лайк";
        head[head.length -1] = lang.equals("ENG") ? "creation date": "дата создания";
        return head;
    }

    public static List<String[]> valuesCollectionItemFields(List<FieldValue> fieldValues, int fieldCount) {
        final List<String[]> fieldData = new ArrayList<>();
        String[] data = new String[fieldCount+2];
        int index = 0;
        int i = 0;
        while (i < fieldValues.size())
        {
            data[index++] = fieldValues.get(i++).getValue();
            if((index & fieldCount ) == fieldCount)
            {
                data[index] = String.valueOf(fieldValues.get(i-1).getItem().getLikes());
                data[index + 1] = String.valueOf(fieldValues.get(i-1).getItem().getCreationDate());
                fieldData.add(data);
                data = new String[fieldCount+2];
                index = 0;
            }
        }
        return fieldData;
    }
}
