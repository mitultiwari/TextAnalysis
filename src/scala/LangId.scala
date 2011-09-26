import TextUtils._;

class LangId {

  def train(filename: String) {
    val myutil = new TextUtils();
    val data = myutil.readFile(filename);
    myutil.computeNGrams(data);
    myutil.printCharMap();
    myutil.storeLangNGramMap("/Users/mtiwari/tmp/testing.ngram.map");
  }

  def computeLangSimilarity(langMapFile: String, teststr: String) : Double = {
    val myutil = new TextUtils();
    myutil.readLangNGramMap(langMapFile);
    myutil.computeLangSimilarity(teststr);
  }

}

object LangId {
  def main(args: Array[String]) {
    println("Hello, world! ");
    // println(toUpper(var1));
    val lang = new LangId();
    lang.train("/Users/mtiwari/tmp/testfile");
    val score = lang.computeLangSimilarity("/Users/mtiwari/tmp/testing.ngram.map", "testing");
    println("testing, score:"+score);
  }
}
