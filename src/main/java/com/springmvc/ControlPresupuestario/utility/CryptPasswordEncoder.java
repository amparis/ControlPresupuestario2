package com.springmvc.ControlPresupuestario.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptPasswordEncoder {

   static public String getPasswordEncoder(String password){

      return new BCryptPasswordEncoder().encode(password);
   }


}
