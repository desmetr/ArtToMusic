package model.music;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utilities.Globals;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by rafael on 22.10.16.
 */
public class MusicData
{
    private static Globals.NoteLength getEnumValue(Element element, String tagName)
    {
        String textValue = null;
        NodeList nodeList = element.getElementsByTagName(tagName);

        if (nodeList != null && nodeList.getLength() > 0)
        {
            Element tempElement = (Element) nodeList.item(0);
            textValue = tempElement.getFirstChild().getNodeValue();

            for (int i = 0; i < Globals.NoteLength.values().length; i++)
            {
                if (textValue.equalsIgnoreCase(Globals.NoteLength.values()[i].toString()))
                {
                    return Globals.NoteLength.values()[i];
                }
            }
        }
        return null;
    }

    private static Double getDoubleValue(Element element, String tagName)
    {
        String textValue = null;
        NodeList nodeList = element.getElementsByTagName(tagName);

        if (nodeList != null && nodeList.getLength() > 0)
        {
            Element tempElement = (Element) nodeList.item(0);
            textValue = tempElement.getFirstChild().getNodeValue();
        }

        return Double.parseDouble(textValue);
    }

    private static Map<Globals.NoteLength, Double> getNoteOffsets()
    {
        Map<Globals.NoteLength, Double> noteOffsets = new HashMap<Globals.NoteLength, Double>();
//
//        SAXBuilder saxBuilder = new SAXBuilder();
//        try
//        {
//            Document document = saxBuilder.build(is);
//            Element root = document.getRootElement();
//
//            XPathFactory xpathFactory = XPathFactory.instance();
//            String titelTextPath = "root/deep/tag/other/text()";
//            XPathExpression expr = xpathFactory.compile(titelTextPath);
//            List<Object> xPathSearchedNodes = expr.evaluate(document);
//            for (int i = 0; i < xPathSearchedNodes.size(); i++)
//            {
//                AbstractDocument.Content content = (AbstractDocument.Content) xPathSearchedNodes.get(i);
//                LOGGER.trace(content.getValue());
//            }
//        }
//        catch (XPathExpressionException e) {    e.printStackTrace();    }

        return noteOffsets;
    }

    public static Vector<Note> generateFourBars()
    {
        // TODO: data read from file
        Map<Globals.NoteLength, Double> noteOffsets = getNoteOffsets();
        Vector<Note> notes = new Vector<Note>();



        return notes;
    }
}
