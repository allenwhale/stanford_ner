import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * 
 * <p>
 * ClassName ExtractDemo
 * </p>
 * <p>
 * Description 加载NER模块
 * </p>
 * 
 * @author wangxu wangx89@126.com
 *         <p>
 *         Date 2015年1月8日 下午2:53:45
 *         </p>
 * @version V1.0.0
 * 
 */
public class ExtractDemo {
	private static AbstractSequenceClassifier<CoreLabel> ner;

	public ExtractDemo() {
		InitNer();
	}

	public void InitNer() {
		String serializedClassifier = "classifiers/chinese.misc.distsim.crf.ser.gz"; // chinese.misc.distsim.crf.ser.gz
		if (ner == null) {
			ner = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
		}
	}

	public String doNer(String sent) {
		return ner.classifyWithInlineXML(sent);
	}

	public static void main(String args[]) {
		String str = "我 去 吃饭 ， 告诉 李强 一声 。";
		ExtractDemo extractDemo = new ExtractDemo();
		System.out.println(extractDemo.doNer(str));
		System.out.println("Complete!");
	}

}