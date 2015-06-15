import java.util.ArrayList;
import java.util.Properties;

import py4j.GatewayServer;
//ClassName ZH_SegDemo  
// 
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class stanford_ner {
	public static CRFClassifier<CoreLabel> segmenter;
	private static ExtractDemo extractdemo;
	static {
		//
		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", "data");
		props.setProperty("serDictionary", "data/dict-chris6.ser.gz");
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");
		segmenter = new CRFClassifier<CoreLabel>(props);
		segmenter.loadClassifierNoExceptions("data/ctb.gz", props);
		segmenter.flags.setProperties(props);
	}

	public String doSegment(String sent) {
		String[] strs = (String[]) segmenter.segmentString(sent).toArray();
		StringBuffer buf = new StringBuffer();
		for (String s : strs) {
			buf.append(s + " ");
		}
		// System.out.println("segmented res: " + buf.toString());
		return buf.toString();
	}
	
	public ArrayList<String> Ner(String seg){
		String data_ner = extractdemo.doNer(seg);
		String[] arys = data_ner.split("<\\w{3,}>");
		ArrayList<String> list = new ArrayList<String>();
		if (arys.length >= 2) {
			for (int i = 1; i < arys.length; i++) {
				String data = arys[i].split("</\\w{3,}>")[0];
				list.add(data);
			}
		}
		return list;
	}
	
	public ArrayList<String> Execute(String msg){
		return Ner(doSegment(msg));
	}
	
	public stanford_ner(){
		extractdemo = new ExtractDemo();
	}
	
	public static void main(String[] args) {
		GatewayServer gatewayServer = new GatewayServer(new stanford_ner());
        gatewayServer.start();
        System.out.println("server started");
	}
}