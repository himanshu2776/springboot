/*      */ package org.json;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Array;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JSONArray
/*      */   implements Iterable<Object>
/*      */ {
/*      */   private final ArrayList<Object> myArrayList;
/*      */   
/*      */   public JSONArray() {
/*   95 */     this.myArrayList = new ArrayList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(JSONTokener x) throws JSONException {
/*  107 */     this();
/*  108 */     if (x.nextClean() != '[') {
/*  109 */       throw x.syntaxError("A JSONArray text must start with '['");
/*      */     }
/*      */     
/*  112 */     char nextChar = x.nextClean();
/*  113 */     if (nextChar == '\000')
/*      */     {
/*  115 */       throw x.syntaxError("Expected a ',' or ']'");
/*      */     }
/*  117 */     if (nextChar != ']') {
/*  118 */       x.back();
/*      */       while (true) {
/*  120 */         if (x.nextClean() == ',') {
/*  121 */           x.back();
/*  122 */           this.myArrayList.add(JSONObject.NULL);
/*      */         } else {
/*  124 */           x.back();
/*  125 */           this.myArrayList.add(x.nextValue());
/*      */         } 
/*  127 */         switch (x.nextClean()) {
/*      */           
/*      */           case '\000':
/*  130 */             throw x.syntaxError("Expected a ',' or ']'");
/*      */           case ',':
/*  132 */             nextChar = x.nextClean();
/*  133 */             if (nextChar == '\000')
/*      */             {
/*  135 */               throw x.syntaxError("Expected a ',' or ']'");
/*      */             }
/*  137 */             if (nextChar == ']') {
/*      */               return;
/*      */             }
/*  140 */             x.back(); continue;
/*      */           case ']':
/*      */             return;
/*      */         }  break;
/*      */       } 
/*  145 */       throw x.syntaxError("Expected a ',' or ']'");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(String source) throws JSONException {
/*  162 */     this(new JSONTokener(source));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(Collection<?> collection) {
/*  172 */     if (collection == null) {
/*  173 */       this.myArrayList = new ArrayList();
/*      */     } else {
/*  175 */       this.myArrayList = new ArrayList(collection.size());
/*  176 */       for (Object o : collection) {
/*  177 */         this.myArrayList.add(JSONObject.wrap(o));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(Object array) throws JSONException {
/*  195 */     this();
/*  196 */     if (array.getClass().isArray()) {
/*  197 */       int length = Array.getLength(array);
/*  198 */       this.myArrayList.ensureCapacity(length);
/*  199 */       for (int i = 0; i < length; i++) {
/*  200 */         put(JSONObject.wrap(Array.get(array, i)));
/*      */       }
/*      */     } else {
/*  203 */       throw new JSONException("JSONArray initial value should be a string or collection or array.");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<Object> iterator() {
/*  210 */     return this.myArrayList.iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(int index) throws JSONException {
/*  223 */     Object object = opt(index);
/*  224 */     if (object == null) {
/*  225 */       throw new JSONException("JSONArray[" + index + "] not found.");
/*      */     }
/*  227 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) throws JSONException {
/*  242 */     Object object = get(index);
/*  243 */     if (object.equals(Boolean.FALSE) || (object instanceof String && ((String)object)
/*      */       
/*  245 */       .equalsIgnoreCase("false")))
/*  246 */       return false; 
/*  247 */     if (object.equals(Boolean.TRUE) || (object instanceof String && ((String)object)
/*      */       
/*  249 */       .equalsIgnoreCase("true"))) {
/*  250 */       return true;
/*      */     }
/*  252 */     throw new JSONException("JSONArray[" + index + "] is not a boolean.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(int index) throws JSONException {
/*  266 */     return getNumber(index).doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(int index) throws JSONException {
/*  280 */     return getNumber(index).floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number getNumber(int index) throws JSONException {
/*  294 */     Object object = get(index);
/*      */     try {
/*  296 */       if (object instanceof Number) {
/*  297 */         return (Number)object;
/*      */       }
/*  299 */       return JSONObject.stringToNumber(object.toString());
/*  300 */     } catch (Exception e) {
/*  301 */       throw new JSONException("JSONArray[" + index + "] is not a number.", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E getEnum(Class<E> clazz, int index) throws JSONException {
/*  320 */     E val = optEnum(clazz, index);
/*  321 */     if (val == null)
/*      */     {
/*      */ 
/*      */       
/*  325 */       throw new JSONException("JSONArray[" + index + "] is not an enum of type " + 
/*  326 */           JSONObject.quote(clazz.getSimpleName()) + ".");
/*      */     }
/*  328 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int index) throws JSONException {
/*  345 */     Object object = get(index);
/*  346 */     BigDecimal val = JSONObject.objectToBigDecimal(object, null);
/*  347 */     if (val == null) {
/*  348 */       throw new JSONException("JSONArray[" + index + "] could not convert to BigDecimal (" + object + ").");
/*      */     }
/*      */     
/*  351 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getBigInteger(int index) throws JSONException {
/*  365 */     Object object = get(index);
/*  366 */     BigInteger val = JSONObject.objectToBigInteger(object, null);
/*  367 */     if (val == null) {
/*  368 */       throw new JSONException("JSONArray[" + index + "] could not convert to BigDecimal (" + object + ").");
/*      */     }
/*      */     
/*  371 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(int index) throws JSONException {
/*  384 */     return getNumber(index).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray getJSONArray(int index) throws JSONException {
/*  398 */     Object object = get(index);
/*  399 */     if (object instanceof JSONArray) {
/*  400 */       return (JSONArray)object;
/*      */     }
/*  402 */     throw new JSONException("JSONArray[" + index + "] is not a JSONArray.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject getJSONObject(int index) throws JSONException {
/*  416 */     Object object = get(index);
/*  417 */     if (object instanceof JSONObject) {
/*  418 */       return (JSONObject)object;
/*      */     }
/*  420 */     throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(int index) throws JSONException {
/*  434 */     return getNumber(index).longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(int index) throws JSONException {
/*  447 */     Object object = get(index);
/*  448 */     if (object instanceof String) {
/*  449 */       return (String)object;
/*      */     }
/*  451 */     throw new JSONException("JSONArray[" + index + "] not a string.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNull(int index) {
/*  462 */     return JSONObject.NULL.equals(opt(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String join(String separator) throws JSONException {
/*  477 */     int len = length();
/*  478 */     if (len == 0) {
/*  479 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  483 */     StringBuilder sb = new StringBuilder(JSONObject.valueToString(this.myArrayList.get(0)));
/*      */     
/*  485 */     for (int i = 1; i < len; i++) {
/*  486 */       sb.append(separator)
/*  487 */         .append(JSONObject.valueToString(this.myArrayList.get(i)));
/*      */     }
/*  489 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  498 */     return this.myArrayList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object opt(int index) {
/*  509 */     return (index < 0 || index >= length()) ? null : 
/*  510 */       this.myArrayList.get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean optBoolean(int index) {
/*  523 */     return optBoolean(index, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean optBoolean(int index, boolean defaultValue) {
/*      */     try {
/*  539 */       return getBoolean(index);
/*  540 */     } catch (Exception e) {
/*  541 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double optDouble(int index) {
/*  555 */     return optDouble(index, Double.NaN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double optDouble(int index, double defaultValue) {
/*  570 */     Number val = optNumber(index, null);
/*  571 */     if (val == null) {
/*  572 */       return defaultValue;
/*      */     }
/*  574 */     double doubleValue = val.doubleValue();
/*      */ 
/*      */ 
/*      */     
/*  578 */     return doubleValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float optFloat(int index) {
/*  591 */     return optFloat(index, Float.NaN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float optFloat(int index, float defaultValue) {
/*  606 */     Number val = optNumber(index, null);
/*  607 */     if (val == null) {
/*  608 */       return defaultValue;
/*      */     }
/*  610 */     float floatValue = val.floatValue();
/*      */ 
/*      */ 
/*      */     
/*  614 */     return floatValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int optInt(int index) {
/*  627 */     return optInt(index, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int optInt(int index, int defaultValue) {
/*  642 */     Number val = optNumber(index, null);
/*  643 */     if (val == null) {
/*  644 */       return defaultValue;
/*      */     }
/*  646 */     return val.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, int index) {
/*  661 */     return optEnum(clazz, index, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, int index, E defaultValue) {
/*      */     try {
/*  680 */       Object val = opt(index);
/*  681 */       if (JSONObject.NULL.equals(val)) {
/*  682 */         return defaultValue;
/*      */       }
/*  684 */       if (clazz.isAssignableFrom(val.getClass()))
/*      */       {
/*      */         
/*  687 */         return (E)val;
/*      */       }
/*      */       
/*  690 */       return Enum.valueOf(clazz, val.toString());
/*  691 */     } catch (IllegalArgumentException e) {
/*  692 */       return defaultValue;
/*  693 */     } catch (NullPointerException e) {
/*  694 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger optBigInteger(int index, BigInteger defaultValue) {
/*  710 */     Object val = opt(index);
/*  711 */     return JSONObject.objectToBigInteger(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal optBigDecimal(int index, BigDecimal defaultValue) {
/*  729 */     Object val = opt(index);
/*  730 */     return JSONObject.objectToBigDecimal(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray optJSONArray(int index) {
/*  742 */     Object o = opt(index);
/*  743 */     return (o instanceof JSONArray) ? (JSONArray)o : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject optJSONObject(int index) {
/*  756 */     Object o = opt(index);
/*  757 */     return (o instanceof JSONObject) ? (JSONObject)o : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long optLong(int index) {
/*  770 */     return optLong(index, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long optLong(int index, long defaultValue) {
/*  785 */     Number val = optNumber(index, null);
/*  786 */     if (val == null) {
/*  787 */       return defaultValue;
/*      */     }
/*  789 */     return val.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number optNumber(int index) {
/*  803 */     return optNumber(index, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number optNumber(int index, Number defaultValue) {
/*  819 */     Object val = opt(index);
/*  820 */     if (JSONObject.NULL.equals(val)) {
/*  821 */       return defaultValue;
/*      */     }
/*  823 */     if (val instanceof Number) {
/*  824 */       return (Number)val;
/*      */     }
/*      */     
/*  827 */     if (val instanceof String) {
/*      */       try {
/*  829 */         return JSONObject.stringToNumber((String)val);
/*  830 */       } catch (Exception e) {
/*  831 */         return defaultValue;
/*      */       } 
/*      */     }
/*  834 */     return defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String optString(int index) {
/*  847 */     return optString(index, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String optString(int index, String defaultValue) {
/*  861 */     Object object = opt(index);
/*  862 */     return JSONObject.NULL.equals(object) ? defaultValue : 
/*  863 */       object.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(boolean value) {
/*  874 */     return put(value ? Boolean.TRUE : Boolean.FALSE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(Collection<?> value) {
/*  888 */     return put(new JSONArray(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(double value) throws JSONException {
/*  901 */     return put(Double.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(float value) throws JSONException {
/*  914 */     return put(Float.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int value) {
/*  925 */     return put(Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(long value) {
/*  936 */     return put(Long.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(Map<?, ?> value) {
/*  952 */     return put(new JSONObject(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(Object value) {
/*  967 */     JSONObject.testValidity(value);
/*  968 */     this.myArrayList.add(value);
/*  969 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, boolean value) throws JSONException {
/*  986 */     return put(index, value ? Boolean.TRUE : Boolean.FALSE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, Collection<?> value) throws JSONException {
/* 1002 */     return put(index, new JSONArray(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, double value) throws JSONException {
/* 1019 */     return put(index, Double.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, float value) throws JSONException {
/* 1036 */     return put(index, Float.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, int value) throws JSONException {
/* 1053 */     return put(index, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, long value) throws JSONException {
/* 1070 */     return put(index, Long.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, Map<?, ?> value) throws JSONException {
/* 1089 */     put(index, new JSONObject(value));
/* 1090 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, Object value) throws JSONException {
/* 1110 */     if (index < 0) {
/* 1111 */       throw new JSONException("JSONArray[" + index + "] not found.");
/*      */     }
/* 1113 */     if (index < length()) {
/* 1114 */       JSONObject.testValidity(value);
/* 1115 */       this.myArrayList.set(index, value);
/* 1116 */       return this;
/*      */     } 
/* 1118 */     if (index == length())
/*      */     {
/* 1120 */       return put(value);
/*      */     }
/*      */ 
/*      */     
/* 1124 */     this.myArrayList.ensureCapacity(index + 1);
/* 1125 */     while (index != length())
/*      */     {
/* 1127 */       this.myArrayList.add(JSONObject.NULL);
/*      */     }
/* 1129 */     return put(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object query(String jsonPointer) {
/* 1152 */     return query(new JSONPointer(jsonPointer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object query(JSONPointer jsonPointer) {
/* 1175 */     return jsonPointer.queryFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object optQuery(String jsonPointer) {
/* 1187 */     return optQuery(new JSONPointer(jsonPointer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object optQuery(JSONPointer jsonPointer) {
/*      */     try {
/* 1200 */       return jsonPointer.queryFrom(this);
/* 1201 */     } catch (JSONPointerException e) {
/* 1202 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object remove(int index) {
/* 1215 */     return (index >= 0 && index < length()) ? 
/* 1216 */       this.myArrayList.remove(index) : 
/* 1217 */       null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean similar(Object other) {
/* 1228 */     if (!(other instanceof JSONArray)) {
/* 1229 */       return false;
/*      */     }
/* 1231 */     int len = length();
/* 1232 */     if (len != ((JSONArray)other).length()) {
/* 1233 */       return false;
/*      */     }
/* 1235 */     for (int i = 0; i < len; i++) {
/* 1236 */       Object valueThis = this.myArrayList.get(i);
/* 1237 */       Object valueOther = ((JSONArray)other).myArrayList.get(i);
/* 1238 */       if (valueThis != valueOther) {
/*      */ 
/*      */         
/* 1241 */         if (valueThis == null) {
/* 1242 */           return false;
/*      */         }
/* 1244 */         if (valueThis instanceof JSONObject) {
/* 1245 */           if (!((JSONObject)valueThis).similar(valueOther)) {
/* 1246 */             return false;
/*      */           }
/* 1248 */         } else if (valueThis instanceof JSONArray) {
/* 1249 */           if (!((JSONArray)valueThis).similar(valueOther)) {
/* 1250 */             return false;
/*      */           }
/* 1252 */         } else if (!valueThis.equals(valueOther)) {
/* 1253 */           return false;
/*      */         } 
/*      */       } 
/* 1256 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject toJSONObject(JSONArray names) throws JSONException {
/* 1272 */     if (names == null || names.isEmpty() || isEmpty()) {
/* 1273 */       return null;
/*      */     }
/* 1275 */     JSONObject jo = new JSONObject(names.length());
/* 1276 */     for (int i = 0; i < names.length(); i++) {
/* 1277 */       jo.put(names.getString(i), opt(i));
/*      */     }
/* 1279 */     return jo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     try {
/* 1297 */       return toString(0);
/* 1298 */     } catch (Exception e) {
/* 1299 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(int indentFactor) throws JSONException {
/* 1331 */     StringWriter sw = new StringWriter();
/* 1332 */     synchronized (sw.getBuffer()) {
/* 1333 */       return write(sw, indentFactor, 0).toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer write(Writer writer) throws JSONException {
/* 1348 */     return write(writer, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
/*      */     try {
/* 1382 */       boolean commanate = false;
/* 1383 */       int length = length();
/* 1384 */       writer.write(91);
/*      */       
/* 1386 */       if (length == 1) {
/*      */         try {
/* 1388 */           JSONObject.writeValue(writer, this.myArrayList.get(0), indentFactor, indent);
/*      */         }
/* 1390 */         catch (Exception e) {
/* 1391 */           throw new JSONException("Unable to write JSONArray value at index: 0", e);
/*      */         } 
/* 1393 */       } else if (length != 0) {
/* 1394 */         int newindent = indent + indentFactor;
/*      */         
/* 1396 */         for (int i = 0; i < length; i++) {
/* 1397 */           if (commanate) {
/* 1398 */             writer.write(44);
/*      */           }
/* 1400 */           if (indentFactor > 0) {
/* 1401 */             writer.write(10);
/*      */           }
/* 1403 */           JSONObject.indent(writer, newindent);
/*      */           try {
/* 1405 */             JSONObject.writeValue(writer, this.myArrayList.get(i), indentFactor, newindent);
/*      */           }
/* 1407 */           catch (Exception e) {
/* 1408 */             throw new JSONException("Unable to write JSONArray value at index: " + i, e);
/*      */           } 
/* 1410 */           commanate = true;
/*      */         } 
/* 1412 */         if (indentFactor > 0) {
/* 1413 */           writer.write(10);
/*      */         }
/* 1415 */         JSONObject.indent(writer, indent);
/*      */       } 
/* 1417 */       writer.write(93);
/* 1418 */       return writer;
/* 1419 */     } catch (IOException e) {
/* 1420 */       throw new JSONException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Object> toList() {
/* 1434 */     List<Object> results = new ArrayList(this.myArrayList.size());
/* 1435 */     for (Object element : this.myArrayList) {
/* 1436 */       if (element == null || JSONObject.NULL.equals(element)) {
/* 1437 */         results.add(null); continue;
/* 1438 */       }  if (element instanceof JSONArray) {
/* 1439 */         results.add(((JSONArray)element).toList()); continue;
/* 1440 */       }  if (element instanceof JSONObject) {
/* 1441 */         results.add(((JSONObject)element).toMap()); continue;
/*      */       } 
/* 1443 */       results.add(element);
/*      */     } 
/*      */     
/* 1446 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1455 */     return this.myArrayList.isEmpty();
/*      */   }
/*      */ }


/* Location:              C:\Users\indor\Downloads\json-20190722.jar!\org\json\JSONArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */