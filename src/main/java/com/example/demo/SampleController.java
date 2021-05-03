package com.example.demo;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.thedeanda.lorem.LoremIpsum;


@RestController
public class SampleController {
	
	long i=0;
	
	@RequestMapping("/healthview")
	public String healthCheck() {
		return "OK";
	}
	
	@RequestMapping(value="/json2xml",method = RequestMethod.POST)
	public String a(@RequestBody String json,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		
		
		String str = null;
		 String xml =null;
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		 xml = convert(json, "root"); // This method converts json object to xml string
		// System.out.println(xml);
		
		//System.out.println(xml);
	

       try {
            final InputSource src = new InputSource(new StringReader(xml));
            final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

        //May need this: System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");


            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.

             str =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" +writer.writeToString(document);

            return str;
       } catch(Exception e) {
    	   String ff = "<root>"+xml+"</root>";
    	 //  System.out.println("oooooooooooooooooooooooooo");
    	 //  System.out.println(ff);
    	   final InputSource src = new InputSource(new StringReader(ff));
           final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
           final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

       //May need this: System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");


           final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
           final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
           final LSSerializer writer = impl.createLSSerializer();

           writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
           writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.
    	   
    	  str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" +writer.writeToString(document);
    	 // System.out.println(str);
       }
      
    
		

		
			
				
	}
			return str;
			
					
			
	}
	

	/*
	 * private static String prettyPrint(Document document) throws
	 * TransformerException { TransformerFactory transformerFactory =
	 * TransformerFactory.newInstance(); Transformer transformer =
	 * transformerFactory.newTransformer();
	 * transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	 * transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
	 * "2"); transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	 * DOMSource source = new DOMSource(document); StringWriter strWriter = new
	 * StringWriter(); StreamResult result = new StreamResult(strWriter);
	 * 
	 * transformer.transform(source, result);
	 * 
	 * return strWriter.getBuffer().toString();
	 * 
	 * }
	 */
	private static Document toXmlDocument(String str) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new InputSource(new StringReader(str)));

		return document;
	}

	public static String convert(String json, String root) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		String xml = XML.toString(jsonObject);

		return xml;
	}

	@RequestMapping(value="/xmltojson",method = RequestMethod.POST)
	public String b(@RequestBody String xml, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		JSONObject soapDatainJsonObject = XML.toJSONObject(xml);

		ObjectMapper mapper = new ObjectMapper();
		Object json = mapper.readValue(soapDatainJsonObject.toString(), Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

		// Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// String jsonOutput = gson.toJson(soapDatainJsonObject);
		//System.out.println(indented);

		return indented;
				}
	}

	/*@RequestMapping(value="/strreverse",method = RequestMethod.POST)
	public String c(@RequestBody String str,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		StringBuffer sb = new StringBuffer();
		sb.append(str);
		String str1 = sb.reverse().toString();
		return str1;

	}
	} */
	/*@RequestMapping(value="/randomstr",method = RequestMethod.POST)
	public String d(@RequestBody String num, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
		int n = Integer.parseInt(num);
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		{

			// chose a Character random from this String
			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

			// create StringBuffer size of AlphaNumericString
			StringBuilder sb = new StringBuilder(n);

			for (int i = 0; i < n; i++) {

				// generate a random number between
				// 0 to AlphaNumericString variable length
				int index = (int) (AlphaNumericString.length() * Math.random());

				// add Character one by one in end of sb
				sb.append(AlphaNumericString.charAt(index));
			}

			return sb.toString();
		}
				}
	} */

	@RequestMapping(value="/webtoip",method = RequestMethod.POST)
	public String e(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		InetAddress ip = null;

		ip = InetAddress.getByName(new URL(s).getHost());

		return "Public IP address of "+s+":" + ip.getHostAddress();

	}}

	/*@RequestMapping(value="/sqrt",method = RequestMethod.POST)
	public Double f(@RequestBody String s, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return 0.0;
			}
				else {

		double n = Integer.parseInt(s);
		Double d = null;
		try {
			d = Math.sqrt(n);
		} catch (Exception e) {

		}
		return d;
	}
	}*/

	@RequestMapping(value="/randomnum",method = RequestMethod.POST)
	public Integer g(@RequestBody String s, @RequestParam(required = false) String qparam,@RequestParam(required = false) String qparam1,HttpServletResponse response) throws IOException {

		int first = Integer.parseInt(s);
		int second = Integer.parseInt(qparam);
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam1);
			if(i !=942109925) {

				return 0;
			}
				else {

		Double k = null;
		Random rand = new Random();
		
		int low = first;
		int high = second;
		int result = rand.nextInt(high-low) + low;

		System.out.println(result);

		return result;
				}

	}

	/*@RequestMapping(value="/encoder",method = RequestMethod.POST)
	public String h(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		// Encode into Base64 format
		String BasicBase64format = Base64.getEncoder().encodeToString(s.getBytes());
		return BasicBase64format;
	}
	}

	@RequestMapping(value="/decoder",method = RequestMethod.POST)
	public String j(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		// Encode into Base64 format
		Base64.Decoder decoder = Base64.getDecoder();
		// Decoding string
		String dStr = new String(decoder.decode(s));
		return dStr;
	}
	}
	@RequestMapping(value="/circlearea",method = RequestMethod.POST)
	public Double k(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return 0.0;
			}
				else {

		double n = Double.parseDouble(s);
		Double a = 3.14 * n * n;
		return a;
				}
	} */

	@RequestMapping(value="/pngtojpeg",method = RequestMethod.POST)
	public String l(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(required = false) String qparam,HttpServletResponse response)
			throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		BufferedImage image = ImageIO.read(file.getInputStream());
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

//ImageIO.write(result, "jpg", new File("D:\\gg.jpg"));

		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ImageIO.write(result, "jpg", bao);
		byte[] bytearr = bao.toByteArray();
		String encodedString = Base64.getEncoder().encodeToString(bytearr);
		return encodedString;

	}}

	/*@RequestMapping(value="/pngtobmp",method = RequestMethod.POST)
	public String m(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(required = false) String qparam,HttpServletResponse response)
			throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		BufferedImage imag = ImageIO.read(file.getInputStream());
		BufferedImage bmpImg = new BufferedImage(imag.getWidth(), imag.getHeight(), BufferedImage.TYPE_INT_RGB);
		bmpImg.createGraphics().drawImage(imag, 0, 0, Color.WHITE, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// ImageIO.write(bmpImg, "bmp", new File("D:\\gddg.bmp"));

		ImageIO.write(bmpImg, "bmp", baos);
		String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());

		return base64String;

	}} */

	/*@RequestMapping(value="/pngtogif",method = RequestMethod.POST)
	public String n(@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(required = false) String qparam, HttpServletResponse response)
			throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		BufferedImage imag = ImageIO.read(file.getInputStream());
		BufferedImage bmpImg = new BufferedImage(imag.getWidth(), imag.getHeight(), BufferedImage.TYPE_INT_RGB);
		bmpImg.createGraphics().drawImage(imag, 0, 0, Color.WHITE, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// ImageIO.write(bmpImg, "bmp", new File("D:\\gddg.bmp"));

		ImageIO.write(bmpImg, "gif", baos);
		String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());

		return base64String;

	}} */

	/*@RequestMapping(value="/giftopng",method = RequestMethod.POST)
	public String o(@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(required = false) String qparam, HttpServletResponse response)
			throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		BufferedImage imag = ImageIO.read(file.getInputStream());
		BufferedImage bmpImg = new BufferedImage(imag.getWidth(), imag.getHeight(), BufferedImage.TYPE_INT_RGB);
		bmpImg.createGraphics().drawImage(imag, 0, 0, Color.WHITE, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// ImageIO.write(bmpImg, "bmp", new File("D:\\gddg.bmp"));

		ImageIO.write(bmpImg, "png", baos);
		String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());

		return base64String;

	}} 

	@RequestMapping(value="/dectobinary",method = RequestMethod.POST)
	public String p(@RequestBody String n,@RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
		int nv = Integer.parseInt(n);
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		String str = Integer.toBinaryString(nv);

		return str;

	}}

	@RequestMapping(value="/dectooct",method = RequestMethod.POST)
	public String q(@RequestBody String n, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		int nv = Integer.parseInt(n);
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		String str = Integer.toOctalString(nv);

		return str;

	}
	}
	@RequestMapping(value="/bintodec",method = RequestMethod.POST)
	public Integer r(@RequestBody String n, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return 0;
			}
				else {


		Integer ii = Integer.parseInt(n, 2);
		return ii;

	}}

	@RequestMapping(value="/bintohex",method = RequestMethod.POST)
	public String s(@RequestBody String n, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		Integer ii = Integer.parseInt(n, 2);
		String str = Integer.toHexString(ii);

		return str;
				}
	}

	@RequestMapping(value="/bintooctal",method = RequestMethod.POST)
	public String t(@RequestBody String n, @RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		Integer i = Integer.parseInt(n, 2);
		String str = Integer.toOctalString(i);

		return str;
				}

	}

	@RequestMapping(value="/hextodec",method = RequestMethod.POST)
	public Integer u(@RequestBody String n,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return 0;
			}
				else {


		Integer num = Integer.parseInt(n, 16);
		return num;

	}
	}
	@RequestMapping(value="/hextobin",method = RequestMethod.POST)
	public String v(@RequestBody String n, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		Integer num = Integer.parseInt(n, 16);
		String str = Integer.toBinaryString(num);
		return str;
				}

	}

	@RequestMapping(value="/hextooct",method = RequestMethod.POST)
	public String w(@RequestBody String n,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		Integer num = Integer.parseInt(n, 16);
		String str = Integer.toOctalString(num);
		return str;
		
				}

	}

	@RequestMapping(value="/octtodec",method = RequestMethod.POST)
	public Integer x(@RequestBody String n,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return 0;
			}
				else {


		int num = Integer.parseInt(n, 8);
		return num;

	}}

	@RequestMapping(value="/octtobinary",method = RequestMethod.POST)
	public String y(@RequestBody String n, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		int num = Integer.parseInt(n, 8);
		String str = Integer.toBinaryString(num);
		return str;
				}
	}

	@RequestMapping(value="/octtohex",method = RequestMethod.POST)
	public String z(@RequestBody String n,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String str1 = Long.toHexString(Long.parseLong(n, 8));

		return str1;
				}
	} */

	/*@RequestMapping(value="/passgen",method = RequestMethod.POST)
	public String randomwords(@RequestBody String nn, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
		Integer len = Integer.parseInt(nn);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String symbols = "!@#$%^&*_=+-/.?<>)";

		String values = Capital_chars + Small_chars + numbers + symbols;

		// Using random method
		Random rndm_method = new Random();

		char[] password = new char[len];

		for (int i = 0; i < len; i++) {

			password[i] = values.charAt(rndm_method.nextInt(values.length()));

		}
		String str = new String(password);
		//System.out.println(str);

		return str;
				}
	}

	@RequestMapping(value="/numtoword",method = RequestMethod.POST)
	public String numtoword(@RequestBody String nn,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {
		
		Integer yy = Integer.parseInt(nn);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String str = SampleController.convert(yy);
		return str;
	}}

	@RequestMapping(value="/wordcounter",method = RequestMethod.POST)
	public Integer wordcounter(@RequestBody String input,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return 0;
			}
				else {


		if (input == null || input.isEmpty()) {
			return 0;
		}

		String[] words = input.split("\\s+");

		return words.length;
	}
	}

	@RequestMapping(value="/encodeurl",method = RequestMethod.POST)
	public String encodeurl(@RequestBody String input,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String encodeURL = URLEncoder.encode(input, "UTF-8");
		return encodeURL;

	}}

	@RequestMapping(value="/decodeurl",method = RequestMethod.POST)
	public String decodeurl(@RequestBody String input, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String decodeURL = URLDecoder.decode(input, "UTF-8");
		return decodeURL;

	}}  */

	/*@RequestMapping(value="/texttohex",method = RequestMethod.POST)
	public String texttohex(@RequestBody String input, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {   */


//	return String.format("%040x", new BigInteger(1, input.getBytes(/* YOUR_CHARSET? */))); 

	//}  
//}       

/*	@RequestMapping(value="/hextotext",method = RequestMethod.POST)
	public String hextotext(@RequestBody String hex,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		hex = hex.replaceAll("^(00)+", "");
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		return new String(bytes);

	}}

	@RequestMapping(value="/strtobinary",method = RequestMethod.POST)
	public String strtobinary(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		byte[] bytes = s.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
			binary.append(' ');
		}
		//System.out.println(binary.toString());
		return binary.toString();
				}
	}

	@RequestMapping(value="/bintostr",method = RequestMethod.POST)
	public String bintostr(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String[] singleBinaryArray = s.toString().split("\\s");
		String finalResult = "";
		for (String string : singleBinaryArray) {
			Character c = (char) Integer.parseInt(string, 2);
			finalResult += c.toString();
		}
		System.out.println("String " + finalResult);
		return finalResult;

	}}
	
	
	@RequestMapping(value="/removeacc",method = RequestMethod.POST)
	public String removeacc(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		// String s = "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô";

		s = s.replaceAll("[èéêë]", "e");
		s = s.replaceAll("[ûù]", "u");
		s = s.replaceAll("[ïî]", "i");
		s = s.replaceAll("[àâ]", "a");
		s = s.replaceAll("Ô", "o");

		s = s.replaceAll("[ÈÉÊË]", "E");
		s = s.replaceAll("[ÛÙ]", "U");
		s = s.replaceAll("[ÏÎ]", "I");
		s = s.replaceAll("[ÀÂ]", "A");
		s = s.replaceAll("Ô", "O");

		return s;
		
				}

	}  */

	@RequestMapping(value="/removeduplines",method = RequestMethod.POST)
	public String removeduplines(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		String[] tokens = s.split("\n");
		StringBuilder resultBuilder = new StringBuilder();
		Set<String> alreadyPresent = new HashSet<String>();

		boolean first = true;
		for (String token : tokens) {

			if (!alreadyPresent.contains(token)) {
				if (first)
					first = false;
				else
					resultBuilder.append("\n");

				if (!alreadyPresent.contains(token))
					resultBuilder.append(token);
			}

			alreadyPresent.add(token);
		}
		String result = resultBuilder.toString();

		return result;

	}}

	@RequestMapping(value="/removeemplines",method = RequestMethod.POST)
	public String removeemplines(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String text = s;
		String adjusted = text.replaceAll("(?m)^[ \t]*\r?\n", "");

		return adjusted;
				}

	}

	/*@RequestMapping(value="/removespaces",method = RequestMethod.POST)
	public String removespaces(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String str = s;
		String str1 = str.replaceAll("\\s+", " ").trim();

		return str1;
		
				}

	}

	@RequestMapping(value="/removeline",method = RequestMethod.POST)
	public String removeline(@RequestBody String s,@RequestParam(required = false) String qparam, @RequestParam(required = false) String a,
			HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		Scanner sc = new Scanner(s);
		String str = null;
		String lineToRemove = a;
		String currentLine;

		StringJoiner joiner = new StringJoiner("");

		while (sc.hasNext()) {
			currentLine = sc.nextLine();

			// trim newline when comparing with lineToRemove
			String trimmedLine = currentLine.trim();
			if (trimmedLine.equals(lineToRemove))
				continue;
			str = currentLine + System.getProperty("line.separator");

			joiner.add(str);

		}
		return joiner.toString();
				}

	}  */

	@RequestMapping(value="/sortline",method = RequestMethod.POST)
	public String sortline(@RequestBody String s, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		StringJoiner joiner = new StringJoiner("\n");
		String inputLine;
		List<String> lineList = new ArrayList<String>();
		Scanner sc = new Scanner(s);
		while (sc.hasNext()) {
			inputLine = sc.nextLine();
			lineList.add(inputLine);
		}

		Collections.sort(lineList);
		String out = null;

		for (String outputLine : lineList) {
			out = outputLine;
			joiner.add(out);

		}
		return joiner.toString();
				}

	}

	@RequestMapping(value="/sortrevline",method = RequestMethod.POST)
	public List sortrevline(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {
		 ArrayList<String> lines = new ArrayList<String>();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		List list = new ArrayList();
		String str = null;
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return (List) list.get(100000);
			}
				else {
					
					  Scanner in = new Scanner(s);
					    while (in.hasNext()) {
					        lines.add(in.nextLine());
					    }

					    in.close();
					    for (int i = lines.size() - 1; i >= 0; i--) {
					       // System.out.println(lines.get(i));
					        str = lines.get(i);
					       // System.out.println(lines.get(i));
                            list.add(str);       
	}
				}
			
			return list;
	}
	
	
	
	
	
	@RequestMapping(value="/loremipsum",method = RequestMethod.POST)
	public String loremipsum(@RequestBody String s,@RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
        int u = Integer.parseInt(s);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		LoremIpsum ipsum = new LoremIpsum();
		String words = ipsum.getParagraphs(2,u);
		String str = ipsum.getFirstNameMale();
		
		 //System.out.println(words);
		return words;
				}

	}

	@RequestMapping(value="/iptohost",method = RequestMethod.POST)
	public String iptohost(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		InetAddress addr = InetAddress.getByName(s);
		return addr.getHostName();
				}
	}

	@SuppressWarnings("resource")
	@RequestMapping(value="/portcheck",method = RequestMethod.POST)
	public String portcheck(@RequestBody String ss, @RequestParam(required = false) String qparam,@RequestParam(required = false) String qparam1,
			HttpServletResponse response) throws IOException, InterruptedException {
		
		int ii = Integer.parseInt(qparam);


		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam1);
		 
			if(i !=942109925) {

				return "";
			}
				else {
					String host = null;
					  Socket Skt;
					  if(ss.equals("localhost") || ss.equals("127.0.0.1")) {
				      host = "localhost";
					       
				        
				      try {
					      
				         Skt = new Socket(host, ii);
				         return "Closed";
				         }
				      
				         catch (UnknownHostException e) {
				        	 return "Closed";
				         
				          
				         } catch (IOException e) {
				        	return "Open";
				         }
				      
				      
				        	
				         }	
					  
					  else {
						  Thread.sleep(100);
						  return "Closed";
					  }
					  
					  
					
				}
			
		
	}

	/*@RequestMapping(value="/jsonescape",method = RequestMethod.POST)
	public String jsonescape(@RequestBody String s,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		String jsonified = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, s);
			jsonified = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonified;
				}

	} */

	@RequestMapping(value="/unjsonescape",method = RequestMethod.POST)
	public String unjsonescape(@RequestBody String input,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		StringBuilder builder = new StringBuilder();

		int i = 0;
		while (i < input.length()) {
			char delimiter = input.charAt(i);
			i++; // consume letter or backslash

			if (delimiter == '\\' && i < input.length()) {

				// consume first after backslash
				char ch = input.charAt(i);
				i++;

				if (ch == '\\' || ch == '/' || ch == '"' || ch == '\'') {
					builder.append(ch);
				} else if (ch == 'n')
					builder.append('\n');
				else if (ch == 'r')
					builder.append('\r');
				else if (ch == 't')
					builder.append('\t');
				else if (ch == 'b')
					builder.append('\b');
				else if (ch == 'f')
					builder.append('\f');
				else if (ch == 'u') {

					StringBuilder hex = new StringBuilder();

					// expect 4 digits
					if (i + 4 > input.length()) {
						throw new RuntimeException("Not enough unicode digits! ");
					}
					for (char x : input.substring(i, i + 4).toCharArray()) {
						if (!Character.isLetterOrDigit(x)) {
							throw new RuntimeException("Bad character in unicode escape.");
						}
						hex.append(Character.toLowerCase(x));
					}
					i += 4; // consume those four digits.

					int code = Integer.parseInt(hex.toString(), 16);
					builder.append((char) code);
				} else {
					throw new RuntimeException("Illegal escape sequence: \\" + ch);
				}
			} else { // it's not a backslash, or it's the last character.
				builder.append(delimiter);
			}
		}
		return builder.toString();
				}

	}

	/*@RequestMapping(value="/wordtohtml",method = RequestMethod.POST)
	public String wordtohtml(@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(required = false) String qparam,
			HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {


		XWPFDocument document = new XWPFDocument(file.getInputStream());
		XHTMLOptions options = XHTMLOptions.create();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XHTMLConverter.getInstance().convert(document, out, options);
		final byte[] bytes = out.toByteArray();
		String encodedString = Base64.getEncoder().encodeToString(bytes);

		return encodedString;
				}

	}

	

	@RequestMapping(value="/jsontoyaml",method = RequestMethod.POST)
	public String jsontoyaml(@RequestBody String input,@RequestParam(required = false) String qparam, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		
		ObjectMapper mapper = new ObjectMapper();

		// convert object to JSON
		String json = mapper.writeValueAsString(input);

		// convert JSON to YAML
		JsonNode jsonNode = mapper.readTree(json);
		String yaml = new YAMLMapper().writeValueAsString(jsonNode);
		return yaml;
	}
	}  */
	@RequestMapping(value="/csvtojson",method = RequestMethod.POST)
	public String csvtojson(@RequestBody String file, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		// File input = new File("input.csv");
	        File output = new File("output.json");
	 
	        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
	        CsvMapper csvMapper = new CsvMapper();
	 
	        // Read data from CSV file
	        List readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file).readAll();
	 
	        ObjectMapper mapper = new ObjectMapper();
	 
	        // Write JSON formated data to output.json file
	        mapper.writerWithDefaultPrettyPrinter().writeValue(output, readAll);

	      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll);
	     
	}}
	
	/*@RequestMapping(value="/xmltoxsl",method = RequestMethod.POST)
	public String jsontohtml(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "file1", required = false) MultipartFile file1, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException, TransformerException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

	//	System.out.println(file.getSize());
	//	System.out.println(file1.getSize());
		
		
	    StringWriter writer = new StringWriter();
	      StreamResult result = new StreamResult(writer);
		
		 TransformerFactory factory = TransformerFactory.newInstance();
	        Source xslt = new StreamSource(file1.getInputStream());
	        Transformer transformer = factory.newTransformer(xslt);

	        Source text = new StreamSource(file.getInputStream());
	        File ff = new  File("D:\\");
	      //  StreamResult sr = new StreamResult(ff);
	      transformer.transform(text, result );
	      String strResult = writer.toString();
	  
	  //    System.out.println(strResult);
	  	
	      return strResult;
				}
	     
	}  */
	
	@RequestMapping(value="/xmltoyaml",method = RequestMethod.POST)
	public String xmltoyaml(@RequestBody String file,@RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
    
		 i = Long.parseLong(qparam);
		if(i !=942109925) {

			return "";
		}
			else {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		String ff = XML.toJSONObject(file).toString();
		
		ObjectMapper mapper = new ObjectMapper();

		// convert JSON to YAML
		JsonNode jsonNode = mapper.readTree(ff);
		String yaml = new YAMLMapper().writeValueAsString(jsonNode);
	    
	     return yaml;
		} 
		
	
		
	}
	
	/*@RequestMapping(value="/jsontocsv",method = RequestMethod.POST)
	public String jsontocsv(@RequestBody String f, @RequestParam(required = false) String qparam,@RequestParam(required = false) String arrname, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		String csv = null;
	      JSONObject output;
	      try {
	         output = new JSONObject(f);
	         JSONArray docs = output.getJSONArray(arrname.trim());
	         File file = new File("EmpDetails.csv");
	          csv = CDL.toString(docs);
	         FileUtils.writeStringToFile(file, csv);
	
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
		return csv;
				}
	     
	}
	
	
	@RequestMapping(value="/jsontotsv",method = RequestMethod.POST)
	public String jsontotsv(@RequestBody String f,@RequestParam(required = false) String qparam, @RequestParam(required = false) String arrname, HttpServletResponse response) throws IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

	
		String csv = null;
	      JSONObject output;
	      try {
	         output = new JSONObject(f);
	         JSONArray docs = output.getJSONArray(arrname.trim());
	         File file = new File("EmpDetails.csv");
	          csv = CDL.toString(docs);
	         FileUtils.writeStringToFile(file, csv);
	
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	      String tsvformatted = csv.replace(",", "  ");
		return tsvformatted;
				}
}
	
	@RequestMapping(value="/csvtotsv",method = RequestMethod.POST)
	public String csvtotsv(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
		String xx=null;
		ArrayList list = new ArrayList();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

	
		String csvFile = "/Users/mkyong/csv/country.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                xx = line.replace(",", " ");
             //  System.out.println(xx);
               list.add(xx);
               // System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return list.toString();
				}
		
}  */

	
	/*@RequestMapping(value="/csvtoxls",method = RequestMethod.POST)
	public String csvtoxls(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletResponse response) throws IOException, TransformerException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		String encodedString =null;
		
		{
	        ArrayList arList=null;
	        ArrayList al=null;
	        String fName = "test.csv";
	        String thisLine;
	        int count=0;
	   //     FileInputStream fis = new FileInputStream(fName);
	        DataInputStream myInput = new DataInputStream(file.getInputStream());
	        int i=0;
	        arList = new ArrayList();
	        while ((thisLine = myInput.readLine()) != null)
	        {
	            al = new ArrayList();
	            String strar[] = thisLine.split(",");
	            for(int j=0;j<strar.length;j++)
	            {
	                al.add(strar[j]);
	            }
	            arList.add(al);
	            System.out.println();
	            i++;
	        }

	        try
	        {
	            HSSFWorkbook hwb = new HSSFWorkbook();
	            HSSFSheet sheet = hwb.createSheet("new sheet");
	            for(int k=0;k<arList.size();k++)
	            {
	                ArrayList ardata = (ArrayList)arList.get(k);
	                HSSFRow row = sheet.createRow((short) 0+k);
	                for(int p=0;p<ardata.size();p++)
	                {
	                    HSSFCell cell = row.createCell((short) p);
	                    String data = ardata.get(p).toString();
	                    if(data.startsWith("=")){
	                        cell.setCellType(Cell.CELL_TYPE_STRING);
	                        data=data.replaceAll("\"", "");
	                        data=data.replaceAll("=", "");
	                        cell.setCellValue(data);
	                    }else if(data.startsWith("\"")){
	                        data=data.replaceAll("\"", "");
	                        cell.setCellType(Cell.CELL_TYPE_STRING);
	                        cell.setCellValue(data);
	                    }else{
	                        data=data.replaceAll("\"", "");
	                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(data);
	                    } */
	                    //*/
	                    // cell.setCellValue(ardata.get(p).toString());
	             //   } 
	            //    System.out.println();
	          //  }
	        //    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            
	        //    hwb.write(stream);

	          //  String finalString = new String(stream.toByteArray());
	        //    final byte[] bytes = stream.toByteArray();
	    	//	 encodedString = Base64.getEncoder().encodeToString(bytes);   

	      //  } catch ( Exception ex ) {
	      //      ex.printStackTrace();
	      //  } //main method ends
	 //   }
	//	return encodedString;
	     
//	}
	
	@RequestMapping(value="/pngtopdf",method = RequestMethod.POST)
	public String pngtopdf(@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(required = false) String qparam,
			HttpServletResponse response) throws IOException {

		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		
		PDDocument doc = null;
		doc = new PDDocument();
		PDPage page = new PDPage();
		doc.addPage(page);

		  BufferedImage awtImage = ImageIO.read(file.getInputStream());
		    PDImageXObject  pdImageXObject = LosslessFactory.createFromImage(doc, awtImage);
		    PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
		    contentStream.drawImage(pdImageXObject, awtImage.getMinX(), awtImage.getMinY(), awtImage.getWidth() / 2, awtImage.getHeight() / 2);
		    contentStream.close();
		    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		    doc.save(byteArrayOutputStream);
		    doc.close();
		    String BasicBase64format = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
		  
		   

		return BasicBase64format;
				}
	}
	
	@RequestMapping(value="/datecon",method = RequestMethod.POST)
	public String datecon(@RequestBody String f,@RequestParam(required = false) String qparam, @RequestParam(required = false) String name, HttpServletResponse response) throws IOException, ParseException {

		
		

		String nameformatted = name.substring(0, 28).concat("+").concat(name.substring(29, 55));
	
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		
		  DateFormat inputFormat = new SimpleDateFormat(
		  "E MMM dd yyyy HH:mm:ss 'GMT'z ", Locale.ENGLISH); 
		  Date date = inputFormat.parse(f);
		  
		  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		  String date11 = formatter.format(date);
		
		  
		  Date date1 = inputFormat.parse(nameformatted); 
		  String date22 = formatter.format(date1);
		
		  
		  DateTimeFormatter formatter1 = 
			        DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.US);
			        LocalDate startDate = LocalDate.parse(date11, formatter1);
			        LocalDate endDate = LocalDate.parse(date22, formatter1);

			        Period period = Period.between(startDate, endDate);
			        String str = String.format("Years : %d , \n\nMonths : %d , \n\nDays : %d, ",
			                period.getYears(), period.getMonths(), period.getDays());
			        
			 
			        if(str.contains("-")) {
			        	return "Difference of Dates is negative, Please choose correct Dates";
			        }
			        else {
			       
return str;
			        }
			        
				}       
		 
	
}
	
	
	@RequestMapping(value="/randwordsgen",method = RequestMethod.POST)
	public List randwordsgen(@RequestBody String s, @RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
	int numberOfWords = Integer.parseInt(s);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return null;
			}
				else {

	
			  List<String> wordList = null;
			System.out.println(numberOfWords);
			  String[] randomStrings = new String[numberOfWords];
			    Random random = new Random();
			    for(int i = 0; i < numberOfWords; i++)
			    {
			        char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
			        for(int j = 0; j < word.length; j++)
			        {
			            word[j] = (char)('a' + random.nextInt(26));
			        }
			        randomStrings[i] = new String(word);
			       wordList = Arrays.asList(randomStrings); 
		
			    }

		
	
		return wordList;
				}
	}
	
	
	private static final String[] tensNames = {
		    "",
		    " ten",
		    " twenty",
		    " thirty",
		    " forty",
		    " fifty",
		    " sixty",
		    " seventy",
		    " eighty",
		    " ninety"
		  };

		  private static final String[] numNames = {
		    "",
		    " one",
		    " two",
		    " three",
		    " four",
		    " five",
		    " six",
		    " seven",
		    " eight",
		    " nine",
		    " ten",
		    " eleven",
		    " twelve",
		    " thirteen",
		    " fourteen",
		    " fifteen",
		    " sixteen",
		    " seventeen",
		    " eighteen",
		    " nineteen"
		  };

		  private SampleController() {}

		  private static String convertLessThanOneThousand(int number) {
		    String soFar;

		    if (number % 100 < 20){
		      soFar = numNames[number % 100];
		      number /= 100;
		    }
		    else {
		      soFar = numNames[number % 10];
		      number /= 10;

		      soFar = tensNames[number % 10] + soFar;
		      number /= 10;
		    }
		    if (number == 0) return soFar;
		    return numNames[number] + " hundred" + soFar;
		  }


		  public static String convert(long number) {
		    // 0 to 999 999 999 999
		    if (number == 0) { return "zero"; }

		    String snumber = Long.toString(number);

		    // pad with "0"
		    String mask = "000000000000";
		    DecimalFormat df = new DecimalFormat(mask);
		    snumber = df.format(number);

		    // XXXnnnnnnnnn
		    int billions = Integer.parseInt(snumber.substring(0,3));
		    // nnnXXXnnnnnn
		    int millions  = Integer.parseInt(snumber.substring(3,6));
		    // nnnnnnXXXnnn
		    int hundredThousands = Integer.parseInt(snumber.substring(6,9));
		    // nnnnnnnnnXXX
		    int thousands = Integer.parseInt(snumber.substring(9,12));

		    String tradBillions;
		    switch (billions) {
		    case 0:
		      tradBillions = "";
		      break;
		    case 1 :
		      tradBillions = convertLessThanOneThousand(billions)
		      + " billion ";
		      break;
		    default :
		      tradBillions = convertLessThanOneThousand(billions)
		      + " billion ";
		    }
		    String result =  tradBillions;

		    String tradMillions;
		    switch (millions) {
		    case 0:
		      tradMillions = "";
		      break;
		    case 1 :
		      tradMillions = convertLessThanOneThousand(millions)
		         + " million ";
		      break;
		    default :
		      tradMillions = convertLessThanOneThousand(millions)
		         + " million ";
		    }
		    result =  result + tradMillions;

		    String tradHundredThousands;
		    switch (hundredThousands) {
		    case 0:
		      tradHundredThousands = "";
		      break;
		    case 1 :
		      tradHundredThousands = "one thousand ";
		      break;
		    default :
		      tradHundredThousands = convertLessThanOneThousand(hundredThousands)
		         + " thousand ";
		    }
		    result =  result + tradHundredThousands;

		    String tradThousand;
		    tradThousand = convertLessThanOneThousand(thousands);
		    result =  result + tradThousand;

		    // remove extra spaces!
		    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
		  }

		/*  @RequestMapping(value="/findip",method = RequestMethod.POST)
			public String findip(@RequestParam(required = false) String qparam,HttpServletResponse response) throws IOException {
            String output = null;
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.addHeader("Access-Control-Allow-Methods", "*");
				response.addHeader("Access-Control-Allow-Headers", "*");
				
				 i = Long.parseLong(qparam);
					if(i !=942109925) {

					
					}
						else {
							Client client = Client.create();

							WebResource webResource = client
							   .resource("https://freegeoip.app/json/");

							ClientResponse response1 = webResource.accept("application/json")
					                   .get(ClientResponse.class);

							if (response1.getStatus() != 200) {
							   throw new RuntimeException("Failed : HTTP error code : "
								+ response1.getStatus());
							}
							 output = response1.getEntity(String.class);  
						}
				//System.out.println(output);
					return output;
			     
			}  */


}
