import scala.collection.mutable.HashMap;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


class TextUtils {

  var charMap = new HashMap[String, Int]; // store n-gram characters count

  def toUpper(var1 : String) : String = {
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
    var i = 0;
    while(i+2 < len){
      var ngram :String = text.substring(i, i+3);
      i = i+1;
      println(ngram);
      var count : Int = (ngramMap.getOrElse(ngram, 0)).toInt;
      count = count + 1; // increment count
      ngramMap.put(ngram, count);
    }
    return ngramMap;
  }

  def computeNGrams(text : String) {
    charMap = returnNGrams(text);
  }

  // TODO:
  // def storeLangNGrams(ngramMap: HashMap[String, Int], filename:String){
  def storeLangNGrams(filename:String){
    val fw = new FileWriter(filename);
    println("Writing file:"+filename);
    fw.write(charMap.toString());
    fw.close()
  }

  // store lang ngram map as an object
  def storeLangNGramMap(filename:String){
    println("Writing lang ngram map into file: "+filename);
    try {
      val oos = new ObjectOutputStream(new FileOutputStream(filename));
      oos.writeObject(charMap);
      oos.close();
    } catch {
      case e: FileNotFoundException => e.printStackTrace();
      case ioe: IOException => ioe.printStackTrace();
    }
  }

  def readLangNGramMap(filename:String){
    println("Reading lang ngram map from file: "+filename);
    try {
      val obj = new ObjectInputStream(new FileInputStream(filename));
      val readObj = obj.readObject();
      charMap = readObj.asInstanceOf[HashMap[String, Int]];
      printCharMap();
    } catch {
      case e: FileNotFoundException => e.printStackTrace();
      case ioe: IOException => ioe.printStackTrace();
      case cfe: ClassNotFoundException => cfe.printStackTrace();
    }
  }

  // compute sum of counts of ngrams in the map
  def sumNGramCounts(ngramMap: HashMap[String, Int]): Double = {
    var sumNGramCount: Double = 0;
    for((ngram, ngramCount) <- ngramMap){
      sumNGramCount = sumNGramCount + ngramCount;
    }
    return sumNGramCount;
  }

  // compute similarity between given string and a language char map
  def computeNGramsSimilarity(langMap: HashMap[String, Int], testMap: HashMap[String, Int]): Double = {
    // TODO: store ratio of ngram_count/sum_ngram_counts in the map
    println("in computeNGramSimiliarity");
    // var langSum = sumNGramCounts(langMap);
    // var testSum = sumNGramCounts(testMap);

    var sumScore: Double = 0;
    var sumTestNGram: Double = 0;
    var sumLangNGram: Double = 0;
    // sumNGram = sumNGramCounts(langMap);
    for((testNGram, nGramCount) <- testMap){
      println(testNGram + " " + nGramCount);
      var langCount = langMap.getOrElse(testNGram, 0);
      sumScore = sumScore + nGramCount * langCount;
      sumTestNGram = sumTestNGram + nGramCount;
      sumLangNGram = sumLangNGram + langCount;
    }
    println("sumScore:"+sumScore+ " sumTestNGram: "+ sumTestNGram+ " sumLangNGram: "+ sumLangNGram);
    if(sumTestNGram*sumLangNGram == 0) return 0;
    return (sumScore/(sumTestNGram*sumLangNGram));
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
    myutil.computeNGrams(data);
    myutil.printCharMap();
    myutil.storeLangNGrams("/Users/mtiwari/tmp/testing.ngram");
    myutil.storeLangNGramMap("/Users/mtiwari/tmp/testing.ngram.map");
    myutil.readLangNGramMap("/Users/mtiwari/tmp/testing.ngram.map");

    // val testStr:String = "testing";
    myutil.computeLangSimilarity("testing");
    myutil.computeLangSimilarity("xyz");


  }
}
