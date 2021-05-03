/*     */ package org.json;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XML
/*     */ {
/*  42 */   public static final Character AMP = Character.valueOf('&');
/*     */ 
/*     */   
/*  45 */   public static final Character APOS = Character.valueOf('\'');
/*     */ 
/*     */   
/*  48 */   public static final Character BANG = Character.valueOf('!');
/*     */ 
/*     */   
/*  51 */   public static final Character EQ = Character.valueOf('=');
/*     */ 
/*     */   
/*  54 */   public static final Character GT = Character.valueOf('>');
/*     */ 
/*     */   
/*  57 */   public static final Character LT = Character.valueOf('<');
/*     */ 
/*     */   
/*  60 */   public static final Character QUEST = Character.valueOf('?');
/*     */ 
/*     */   
/*  63 */   public static final Character QUOT = Character.valueOf('"');
/*     */ 
/*     */   
/*  66 */   public static final Character SLASH = Character.valueOf('/');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String NULL_ATTR = "xsi:nil";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Iterable<Integer> codePointIterator(final String string) {
/*  85 */     return new Iterable<Integer>()
/*     */       {
/*     */         public Iterator<Integer> iterator() {
/*  88 */           return new Iterator<Integer>() {
/*  89 */               private int nextIndex = 0;
/*  90 */               private int length = string.length();
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/*  94 */                 return (this.nextIndex < this.length);
/*     */               }
/*     */ 
/*     */               
/*     */               public Integer next() {
/*  99 */                 int result = string.codePointAt(this.nextIndex);
/* 100 */                 this.nextIndex += Character.charCount(result);
/* 101 */                 return Integer.valueOf(result);
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 106 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String escape(String string) {
/* 129 */     StringBuilder sb = new StringBuilder(string.length());
/* 130 */     for (Iterator<Integer> iterator = codePointIterator(string).iterator(); iterator.hasNext(); ) { int cp = ((Integer)iterator.next()).intValue();
/* 131 */       switch (cp) {
/*     */         case 38:
/* 133 */           sb.append("&amp;");
/*     */           continue;
/*     */         case 60:
/* 136 */           sb.append("&lt;");
/*     */           continue;
/*     */         case 62:
/* 139 */           sb.append("&gt;");
/*     */           continue;
/*     */         case 34:
/* 142 */           sb.append("&quot;");
/*     */           continue;
/*     */         case 39:
/* 145 */           sb.append("&apos;");
/*     */           continue;
/*     */       } 
/* 148 */       if (mustEscape(cp)) {
/* 149 */         sb.append("&#x");
/* 150 */         sb.append(Integer.toHexString(cp));
/* 151 */         sb.append(';'); continue;
/*     */       } 
/* 153 */       sb.appendCodePoint(cp); }
/*     */ 
/*     */ 
/*     */     
/* 157 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean mustEscape(int cp) {
/* 173 */     return ((Character.isISOControl(cp) && cp != 9 && cp != 10 && cp != 13) || ((cp < 32 || cp > 55295) && (cp < 57344 || cp > 65533) && (cp < 65536 || cp > 1114111)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String unescape(String string) {
/* 194 */     StringBuilder sb = new StringBuilder(string.length());
/* 195 */     for (int i = 0, length = string.length(); i < length; i++) {
/* 196 */       char c = string.charAt(i);
/* 197 */       if (c == '&') {
/* 198 */         int semic = string.indexOf(';', i);
/* 199 */         if (semic > i) {
/* 200 */           String entity = string.substring(i + 1, semic);
/* 201 */           sb.append(XMLTokener.unescapeEntity(entity));
/*     */           
/* 203 */           i += entity.length() + 1;
/*     */         }
/*     */         else {
/*     */           
/* 207 */           sb.append(c);
/*     */         } 
/*     */       } else {
/*     */         
/* 211 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 214 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void noSpace(String string) throws JSONException {
/* 226 */     int length = string.length();
/* 227 */     if (length == 0) {
/* 228 */       throw new JSONException("Empty string.");
/*     */     }
/* 230 */     for (int i = 0; i < length; i++) {
/* 231 */       if (Character.isWhitespace(string.charAt(i))) {
/* 232 */         throw new JSONException("'" + string + "' contains a space character.");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean parse(XMLTokener x, JSONObject context, String name, XMLParserConfiguration config) throws JSONException {
/* 254 */     JSONObject jsonobject = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     Object token = x.nextToken();
/*     */ 
/*     */ 
/*     */     
/* 273 */     if (token == BANG) {
/* 274 */       char c = x.next();
/* 275 */       if (c == '-') {
/* 276 */         if (x.next() == '-') {
/* 277 */           x.skipPast("-->");
/* 278 */           return false;
/*     */         } 
/* 280 */         x.back();
/* 281 */       } else if (c == '[') {
/* 282 */         token = x.nextToken();
/* 283 */         if ("CDATA".equals(token) && 
/* 284 */           x.next() == '[') {
/* 285 */           String string = x.nextCDATA();
/* 286 */           if (string.length() > 0) {
/* 287 */             context.accumulate(config.cDataTagName, string);
/*     */           }
/* 289 */           return false;
/*     */         } 
/*     */         
/* 292 */         throw x.syntaxError("Expected 'CDATA['");
/*     */       } 
/* 294 */       int i = 1;
/*     */       while (true)
/* 296 */       { token = x.nextMeta();
/* 297 */         if (token == null)
/* 298 */           throw x.syntaxError("Missing '>' after '<!'."); 
/* 299 */         if (token == LT) {
/* 300 */           i++;
/* 301 */         } else if (token == GT) {
/* 302 */           i--;
/*     */         } 
/* 304 */         if (i <= 0)
/* 305 */           return false;  } 
/* 306 */     }  if (token == QUEST) {
/*     */ 
/*     */       
/* 309 */       x.skipPast("?>");
/* 310 */       return false;
/* 311 */     }  if (token == SLASH) {
/*     */ 
/*     */ 
/*     */       
/* 315 */       token = x.nextToken();
/* 316 */       if (name == null) {
/* 317 */         throw x.syntaxError("Mismatched close tag " + token);
/*     */       }
/* 319 */       if (!token.equals(name)) {
/* 320 */         throw x.syntaxError("Mismatched " + name + " and " + token);
/*     */       }
/* 322 */       if (x.nextToken() != GT) {
/* 323 */         throw x.syntaxError("Misshaped close tag");
/*     */       }
/* 325 */       return true;
/*     */     } 
/* 327 */     if (token instanceof Character) {
/* 328 */       throw x.syntaxError("Misshaped tag");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 333 */     String tagName = (String)token;
/* 334 */     token = null;
/* 335 */     jsonobject = new JSONObject();
/* 336 */     boolean nilAttributeFound = false;
/*     */     while (true) {
/* 338 */       if (token == null) {
/* 339 */         token = x.nextToken();
/*     */       }
/*     */       
/* 342 */       if (token instanceof String) {
/* 343 */         String string = (String)token;
/* 344 */         token = x.nextToken();
/* 345 */         if (token == EQ) {
/* 346 */           token = x.nextToken();
/* 347 */           if (!(token instanceof String)) {
/* 348 */             throw x.syntaxError("Missing value");
/*     */           }
/*     */           
/* 351 */           if (config.convertNilAttributeToNull && "xsi:nil"
/* 352 */             .equals(string) && 
/* 353 */             Boolean.parseBoolean((String)token)) {
/* 354 */             nilAttributeFound = true;
/* 355 */           } else if (!nilAttributeFound) {
/* 356 */             jsonobject.accumulate(string, 
/* 357 */                 config.keepStrings ? 
/* 358 */                 token : 
/* 359 */                 stringToValue((String)token));
/*     */           } 
/* 361 */           token = null; continue;
/*     */         } 
/* 363 */         jsonobject.accumulate(string, ""); continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 367 */     if (token == SLASH) {
/*     */       
/* 369 */       if (x.nextToken() != GT) {
/* 370 */         throw x.syntaxError("Misshaped tag");
/*     */       }
/* 372 */       if (nilAttributeFound) {
/* 373 */         context.accumulate(tagName, JSONObject.NULL);
/* 374 */       } else if (jsonobject.length() > 0) {
/* 375 */         context.accumulate(tagName, jsonobject);
/*     */       } else {
/* 377 */         context.accumulate(tagName, "");
/*     */       } 
/* 379 */       return false;
/*     */     } 
/* 381 */     if (token == GT) {
/*     */       while (true) {
/*     */         
/* 384 */         token = x.nextContent();
/* 385 */         if (token == null) {
/* 386 */           if (tagName != null) {
/* 387 */             throw x.syntaxError("Unclosed tag " + tagName);
/*     */           }
/* 389 */           return false;
/* 390 */         }  if (token instanceof String) {
/* 391 */           String string = (String)token;
/* 392 */           if (string.length() > 0)
/* 393 */             jsonobject.accumulate(config.cDataTagName, 
/* 394 */                 config.keepStrings ? string : stringToValue(string)); 
/*     */           continue;
/*     */         } 
/* 397 */         if (token == LT)
/*     */         {
/* 399 */           if (parse(x, jsonobject, tagName, config)) {
/* 400 */             if (jsonobject.length() == 0) {
/* 401 */               context.accumulate(tagName, "");
/* 402 */             } else if (jsonobject.length() == 1 && jsonobject
/* 403 */               .opt(config.cDataTagName) != null) {
/* 404 */               context.accumulate(tagName, jsonobject
/* 405 */                   .opt(config.cDataTagName));
/*     */             } else {
/* 407 */               context.accumulate(tagName, jsonobject);
/*     */             } 
/* 409 */             return false;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 414 */     throw x.syntaxError("Misshaped tag");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object stringToValue(String string) {
/* 429 */     if (string.equals("")) {
/* 430 */       return string;
/*     */     }
/* 432 */     if (string.equalsIgnoreCase("true")) {
/* 433 */       return Boolean.TRUE;
/*     */     }
/* 435 */     if (string.equalsIgnoreCase("false")) {
/* 436 */       return Boolean.FALSE;
/*     */     }
/* 438 */     if (string.equalsIgnoreCase("null")) {
/* 439 */       return JSONObject.NULL;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 447 */     char initial = string.charAt(0);
/* 448 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*     */       
/*     */       try {
/*     */         
/* 452 */         if (string.indexOf('.') > -1 || string.indexOf('e') > -1 || string
/* 453 */           .indexOf('E') > -1 || "-0".equals(string)) {
/* 454 */           Double d = Double.valueOf(string);
/* 455 */           if (!d.isInfinite() && !d.isNaN()) {
/* 456 */             return d;
/*     */           }
/*     */         } else {
/* 459 */           Long myLong = Long.valueOf(string);
/* 460 */           if (string.equals(myLong.toString())) {
/* 461 */             if (myLong.longValue() == myLong.intValue()) {
/* 462 */               return Integer.valueOf(myLong.intValue());
/*     */             }
/* 464 */             return myLong;
/*     */           } 
/*     */         } 
/* 467 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 470 */     return string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(String string) throws JSONException {
/* 490 */     return toJSONObject(string, XMLParserConfiguration.ORIGINAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(Reader reader) throws JSONException {
/* 509 */     return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(Reader reader, boolean keepStrings) throws JSONException {
/* 533 */     if (keepStrings) {
/* 534 */       return toJSONObject(reader, XMLParserConfiguration.KEEP_STRINGS);
/*     */     }
/* 536 */     return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(Reader reader, XMLParserConfiguration config) throws JSONException {
/* 559 */     JSONObject jo = new JSONObject();
/* 560 */     XMLTokener x = new XMLTokener(reader);
/* 561 */     while (x.more()) {
/* 562 */       x.skipPast("<");
/* 563 */       if (x.more()) {
/* 564 */         parse(x, jo, null, config);
/*     */       }
/*     */     } 
/* 567 */     return jo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
/* 592 */     return toJSONObject(new StringReader(string), keepStrings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(String string, XMLParserConfiguration config) throws JSONException {
/* 616 */     return toJSONObject(new StringReader(string), config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(Object object) throws JSONException {
/* 628 */     return toString(object, null, XMLParserConfiguration.ORIGINAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(Object object, String tagName) {
/* 642 */     return toString(object, tagName, XMLParserConfiguration.ORIGINAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(Object object, String tagName, XMLParserConfiguration config) throws JSONException {
/* 659 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 664 */     if (object instanceof JSONObject) {
/*     */ 
/*     */       
/* 667 */       if (tagName != null) {
/* 668 */         sb.append('<');
/* 669 */         sb.append(tagName);
/* 670 */         sb.append('>');
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 675 */       JSONObject jo = (JSONObject)object;
/* 676 */       for (String key : jo.keySet()) {
/* 677 */         Object value = jo.opt(key);
/* 678 */         if (value == null) {
/* 679 */           value = "";
/* 680 */         } else if (value.getClass().isArray()) {
/* 681 */           value = new JSONArray(value);
/*     */         } 
/*     */ 
/*     */         
/* 685 */         if (key.equals(config.cDataTagName)) {
/* 686 */           if (value instanceof JSONArray) {
/* 687 */             JSONArray ja = (JSONArray)value;
/* 688 */             int jaLength = ja.length();
/*     */             
/* 690 */             for (int i = 0; i < jaLength; i++) {
/* 691 */               if (i > 0) {
/* 692 */                 sb.append('\n');
/*     */               }
/* 694 */               Object val = ja.opt(i);
/* 695 */               sb.append(escape(val.toString()));
/*     */             }  continue;
/*     */           } 
/* 698 */           sb.append(escape(value.toString()));
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 703 */         if (value instanceof JSONArray) {
/* 704 */           JSONArray ja = (JSONArray)value;
/* 705 */           int jaLength = ja.length();
/*     */           
/* 707 */           for (int i = 0; i < jaLength; i++) {
/* 708 */             Object val = ja.opt(i);
/* 709 */             if (val instanceof JSONArray) {
/* 710 */               sb.append('<');
/* 711 */               sb.append(key);
/* 712 */               sb.append('>');
/* 713 */               sb.append(toString(val, null, config));
/* 714 */               sb.append("</");
/* 715 */               sb.append(key);
/* 716 */               sb.append('>');
/*     */             } else {
/* 718 */               sb.append(toString(val, key, config));
/*     */             } 
/*     */           }  continue;
/* 721 */         }  if ("".equals(value)) {
/* 722 */           sb.append('<');
/* 723 */           sb.append(key);
/* 724 */           sb.append("/>");
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 729 */         sb.append(toString(value, key, config));
/*     */       } 
/*     */       
/* 732 */       if (tagName != null) {
/*     */ 
/*     */         
/* 735 */         sb.append("</");
/* 736 */         sb.append(tagName);
/* 737 */         sb.append('>');
/*     */       } 
/* 739 */       return sb.toString();
/*     */     } 
/*     */ 
/*     */     
/* 743 */     if (object != null && (object instanceof JSONArray || object.getClass().isArray())) {
/* 744 */       JSONArray ja; if (object.getClass().isArray()) {
/* 745 */         ja = new JSONArray(object);
/*     */       } else {
/* 747 */         ja = (JSONArray)object;
/*     */       } 
/* 749 */       int jaLength = ja.length();
/*     */       
/* 751 */       for (int i = 0; i < jaLength; i++) {
/* 752 */         Object val = ja.opt(i);
/*     */ 
/*     */ 
/*     */         
/* 756 */         sb.append(toString(val, (tagName == null) ? "array" : tagName, config));
/*     */       } 
/* 758 */       return sb.toString();
/*     */     } 
/*     */     
/* 761 */     String string = (object == null) ? "null" : escape(object.toString());
/* 762 */     return (tagName == null) ? ("\"" + string + "\"") : (
/* 763 */       (string.length() == 0) ? ("<" + tagName + "/>") : (
/* 764 */       "<" + tagName + ">" + string + "</" + tagName + ">"));
/*     */   }
/*     */ }


/* Location:              C:\Users\indor\Downloads\json-20190722.jar!\org\json\XML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */