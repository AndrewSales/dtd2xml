package com.andrewsales.tools.dtd2xml;

public class UnicodeFormatter
{

   public static String byteToHex( byte b )
   {
      // Returns hex String representation of byte b
      char hexDigit[] =
      {
         '0', '1', '2', '3', '4', '5', '6', '7',
         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
      };
      char[] array = { hexDigit[ ( b >> 4 ) & 0x0f ], hexDigit[ b & 0x0f ] };
      return new String( array );
   }

   public static String charToHex( char c )
   {
      // Returns hex String representation of char c
      byte hi = ( byte ) ( c >>> 8 );
      byte lo = ( byte ) ( c & 0xff );
      return byteToHex( hi ) + byteToHex( lo );
   }

   /**
    * Encodes a string with US-ASCII escaping
    */
   public static StringBuffer encodeAsAsciiXml( String s )
   {
        StringBuffer es = new StringBuffer();
        char[] ca = s.toCharArray();
        for( int i = 0; i < ca.length; i++ )
        {
            if( ca[ i ] >= '\uD800' && ca[ i ] <= '\uDBFF' )    //surrogate pair
            {
                System.err.println((int)ca[i]);
            }
            else if( ca[ i ] > 127 )
            {
                es.append( "&#x" ).append( charToHex( ca[ i ] ) ).append( ";" );
            }
            else
            {
                es.append( ca[ i ] );
            }
        }
        return es;
   }

}
