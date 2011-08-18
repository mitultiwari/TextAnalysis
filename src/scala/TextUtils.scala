import scala.collection.mutable.HashMap;

class TextUtils {

  var charMap = new HashMap[String, Int]; // store n-gram characters count

  def toUpper(var1 : String) : String = {
    // println(var1.toUpperCase);
    return var1.toUpperCase;
  }

  // TODO: clean text
  // remove extra spaces, newline, convert tabs to space
  def cleanText(text: String) : String = {
    return text;
  }

  // compute n-grams for string and store counts for each n-gram in charMap
  def returnNGrams(text : String) : HashMap[String, Int] = {
    var ngramMap = new HashMap[String, Int];
    var len = text.length();
    var max_index = ((len/3).toInt)*3;
    println(max_index);
    // for(i <- 0 until (max_index-2) ){
    var i = 0;
    while(i+2 < len){
      var ngram :String = text.substring(i, i+3);
      i = i+1;
      println(ngram);
      var count : Int = (ngramMap.getOrElse(ngram, 0)).toInt;
      count = count + 1;
      ngramMap.put(ngram, count); // increment count
    }
    return ngramMap;
  }

  def computeNGrams(text : String) {
    charMap = returnNGrams(text);
  }

  // TODO: compute sum of counts of ngrams in the map
  def sumNGramCounts(ngramMap: HashMap[String, Int]): Int = {
    return 1;
  }

  // TODO: compute similarity between given string and a language char map
  def computeNGramsSimilarity(langMap: HashMap[String, Int], testMap: HashMap[String, Int]): Double = {
    // TODO: store ratio of ngram_count/sum_ngram_counts in the map
    println("in computeNGramSimiliarity");
    var langSum = sumNGramCounts(langMap);
    var testSum = sumNGramCounts(testMap);

    var sumScore: Double = 0;
    for((testNGram, nGramCount) <- testMap){
      println(testNGram + " " + nGramCount);
      var langCount = langMap.getOrElse(testNGram, 0);
      sumScore = sumScore + nGramCount * langCount;
    }
    return sumScore;
  }

  // compute similarity between given string and a language char map
  def computeSimilarity(langMap: HashMap[String, Int], testStr: String): Double = {
    println("in computeSimilarity");
    var testMap = returnNGrams(testStr);
    var similarity = computeNGramsSimilarity(langMap, testMap);
    return similarity;
  }

  // compute similarity
  def computeLangSimilarity(testStr: String): Double = {
    var similarity = computeSimilarity(charMap, testStr);
    println("computeLangSimilarity(): similarity: " + similarity);
    return similarity;
  }

  // read a file in a string and return the string
  def readFile(fname : String) : String = {
    val lines = scala.io.Source.fromFile(fname, "utf-8").mkString;
    println(lines);
    return lines;
  }

  def printCharMap() {
    println(charMap);
  }
}

object TextUtils {
  def main(args: Array[String]) {
    println("Hello, world! ");
    // println(toUpper(var1));
    val myutil = new TextUtils();
    val data = myutil.readFile("/Users/mtiwari/tmp/testfile")
    val testStr:String = "testing";
    myutil.computeNGrams(data);
    myutil.printCharMap();
    myutil.computeLangSimilarity(testStr);
  }
}
