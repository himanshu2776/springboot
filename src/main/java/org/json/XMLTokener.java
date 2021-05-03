/*     */ package org.json;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ public class XMLTokener
/*     */   extends JSONTokener
/*     */ {
/*  44 */   public static final HashMap<String, Character> entity = new HashMap<String, Character>(8); static {
/*  45 */     entity.put("amp", XML.AMP);
/*  46 */     entity.put("apos", XML.APOS);
/*  47 */     entity.put("gt", XML.GT);
/*  48 */     entity.put("lt", XML.LT);
/*  49 */     entity.put("quot", XML.QUOT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLTokener(Reader r) {
/*  57 */     super(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLTokener(String s) {
/*  65 */     super(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nextCDATA() throws JSONException {
/*  76 */     StringBuilder sb = new StringBuilder();
/*  77 */     while (more()) {
/*  78 */       char c = next();
/*  79 */       sb.append(c);
/*  80 */       int i = sb.length() - 3;
/*  81 */       if (i >= 0 && sb.charAt(i) == ']' && sb
/*  82 */         .charAt(i + 1) == ']' && sb.charAt(i + 2) == '>') {
/*  83 */         sb.setLength(i);
/*  84 */         return sb.toString();
/*     */       } 
/*     */     } 
/*  87 */     throw syntaxError("Unclosed CDATA");
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
/*     */   public Object nextContent() throws JSONException {
/*     */     while (true) {
/* 104 */       char c = next();
/* 105 */       if (!Character.isWhitespace(c)) {
/* 106 */         if (c == '\000') {
/* 107 */           return null;
/*     */         }
/* 109 */         if (c == '<') {
/* 110 */           return XML.LT;
/*     */         }
/* 112 */         StringBuilder sb = new StringBuilder();
/*     */         while (true) {
/* 114 */           if (c == '\000') {
/* 115 */             return sb.toString().trim();
/*     */           }
/* 117 */           if (c == '<') {
/* 118 */             back();
/* 119 */             return sb.toString().trim();
/*     */           } 
/* 121 */           if (c == '&') {
/* 122 */             sb.append(nextEntity(c));
/*     */           } else {
/* 124 */             sb.append(c);
/*     */           } 
/* 126 */           c = next();
/*     */         }       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object nextEntity(char ampersand) throws JSONException {
/*     */     char c;
/* 139 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 141 */       c = next();
/* 142 */       if (Character.isLetterOrDigit(c) || c == '#')
/* 143 */       { sb.append(Character.toLowerCase(c)); continue; }  break;
/* 144 */     }  if (c == ';') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       String string = sb.toString();
/* 151 */       return unescapeEntity(string);
/*     */     } 
/*     */     throw syntaxError("Missing ';' in XML entity: &" + sb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String unescapeEntity(String e) {
/* 161 */     if (e == null || e.isEmpty()) {
/* 162 */       return "";
/*     */     }
/*     */     
/* 165 */     if (e.charAt(0) == '#') {
/*     */       int cp;
/* 167 */       if (e.charAt(1) == 'x') {
/*     */         
/* 169 */         cp = Integer.parseInt(e.substring(2), 16);
/*     */       } else {
/*     */         
/* 172 */         cp = Integer.parseInt(e.substring(1));
/*     */       } 
/* 174 */       return new String(new int[] { cp }, 0, 1);
/*     */     } 
/* 176 */     Character knownEntity = entity.get(e);
/* 177 */     if (knownEntity == null)
/*     */     {
/* 179 */       return '&' + e + ';';
/*     */     }
/* 181 */     return knownEntity.toString();
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
/*     */   public Object nextMeta() throws JSONException {
/*     */     char c, q;
/*     */     do {
/* 198 */       c = next();
/* 199 */     } while (Character.isWhitespace(c));
/* 200 */     switch (c) {
/*     */       case '\000':
/* 202 */         throw syntaxError("Misshaped meta tag");
/*     */       case '<':
/* 204 */         return XML.LT;
/*     */       case '>':
/* 206 */         return XML.GT;
/*     */       case '/':
/* 208 */         return XML.SLASH;
/*     */       case '=':
/* 210 */         return XML.EQ;
/*     */       case '!':
/* 212 */         return XML.BANG;
/*     */       case '?':
/* 214 */         return XML.QUEST;
/*     */       case '"':
/*     */       case '\'':
/* 217 */         q = c;
/*     */         while (true) {
/* 219 */           c = next();
/* 220 */           if (c == '\000') {
/* 221 */             throw syntaxError("Unterminated string");
/*     */           }
/* 223 */           if (c == q) {
/* 224 */             return Boolean.TRUE;
/*     */           }
/*     */         } 
/*     */     } 
/*     */     while (true) {
/* 229 */       c = next();
/* 230 */       if (Character.isWhitespace(c)) {
/* 231 */         return Boolean.TRUE;
/*     */       }
/* 233 */       switch (c) { case '\000':
/*     */         case '!':
/*     */         case '"':
/*     */         case '\'':
/*     */         case '/':
/*     */         case '<':
/*     */         case '=':
/*     */         case '>':
/*     */         case '?':
/*     */           break; } 
/* 243 */     }   }
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
/*     */   public Object nextToken() throws JSONException {
/*     */     char c, q;
/*     */     do {
/* 264 */       c = next();
/* 265 */     } while (Character.isWhitespace(c));
StringBuilder sb;
/* 266 */     switch (c) {
/*     */       case '\000':
/* 268 */         throw syntaxError("Misshaped element");
/*     */       case '<':
/* 270 */         throw syntaxError("Misplaced '<'");
/*     */       case '>':
/* 272 */         return XML.GT;
/*     */       case '/':
/* 274 */         return XML.SLASH;
/*     */       case '=':
/* 276 */         return XML.EQ;
/*     */       case '!':
/* 278 */         return XML.BANG;
/*     */       case '?':
/* 280 */         return XML.QUEST;
/*     */ 
/*     */ 
/*     */       
/*     */       case '"':
/*     */       case '\'':
/* 286 */         q = c;
/* 287 */         sb = new StringBuilder();
/*     */         while (true) {
/* 289 */           c = next();
/* 290 */           if (c == '\000') {
/* 291 */             throw syntaxError("Unterminated string");
/*     */           }
/* 293 */           if (c == q) {
/* 294 */             return sb.toString();
/*     */           }
/* 296 */           if (c == '&') {
/* 297 */             sb.append(nextEntity(c)); continue;
/*     */           } 
/* 299 */           sb.append(c);
/*     */         } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     StringBuilder sb1 = new StringBuilder();
/*     */     while (true)
/* 308 */     { sb1.append(c);
/* 309 */       c = next();
/* 310 */       if (Character.isWhitespace(c)) {
/* 311 */         return sb1.toString();
/*     */       }
/* 313 */       switch (c)
/*     */       { case '\000':
/* 315 */           return sb1.toString();
/*     */         case '!':
/*     */         case '/':
/*     */         case '=':
/*     */         case '>':
/*     */         case '?':
/*     */         case '[':
/*     */         case ']':
/* 323 */           back();
/* 324 */           return sb1.toString();
/*     */         case '"':
/*     */         case '\'':
/*     */         case '<':
/* 328 */           break; }  }   }
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
/*     */   public void skipPast(String to) {
/* 348 */     int offset = 0;
/* 349 */     int length = to.length();
/* 350 */     char[] circle = new char[length];
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */     
/* 357 */     for (i = 0; i < length; i++) {
/* 358 */       char c = next();
/* 359 */       if (c == '\000') {
/*     */         return;
/*     */       }
/* 362 */       circle[i] = c;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 368 */       int j = offset;
/* 369 */       boolean b = true;
/*     */ 
/*     */ 
/*     */       
/* 373 */       for (i = 0; i < length; i++) {
/* 374 */         if (circle[j] != to.charAt(i)) {
/* 375 */           b = false;
/*     */           break;
/*     */         } 
/* 378 */         j++;
/* 379 */         if (j >= length) {
/* 380 */           j -= length;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 386 */       if (b) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 392 */       char c = next();
/* 393 */       if (c == '\000') {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 400 */       circle[offset] = c;
/* 401 */       offset++;
/* 402 */       if (offset >= length)
/* 403 */         offset -= length; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\indor\Downloads\json-20190722.jar!\org\json\XMLTokener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */