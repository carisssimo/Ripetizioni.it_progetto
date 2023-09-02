<template>
  <div class="hello">
    <div class="title-container container">
      <h1 class="title-ripetizioni">Ripetizioni.it</h1>
      <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua città</p>
    </div>
    <!--FORM-->
    <div v-if="!isLogged">
      <div class="form-container container align-items-center">
        <form v-on:submit.prevent="submitForm">
          <div class=" form-group">
            <label for="email">Email address</label>
            <input id="email" v-model="email" aria-describedby="emailHelp" class="form-group-orange form-control"
                   required type="email" autocomplete="family-name">
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input id="password" v-model="password" class="form-group-orange form-control" required type="password">
          </div>
          <button class="btn-login btn btn-primary" >Accedi</button>
        </form>
      </div>

      <div class="button-container container align-items-center">
        <RouterLink to="/signin"><a class="btn-login btn btn-primary" role="button">Registrati</a></RouterLink>
      </div>
    </div>
    <div v-if="isLogged">
    <div class="logged-container title-container container">
        <h1 class="title-main">Bentornato!</h1>
        <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua
          città selezionando le date e gli orari che ti fanno più comodo.Prenota la tua
          lezione oppure dai un'occhiata alle lezioni già prenotate!!</p>
        <RouterLink to="/PersonalPage"><a class="btn-l btn-login btn btn-success" role="button">Inizia</a></RouterLink>
        <span style="padding: 0 10px;"></span>
        <div v-if="admin">
          <div :style="{ paddingTop: '20px', paddingBottom: '20px' }"></div>
          <RouterLink to="/Admin"><a class="btn-l btn-login btn btn-success" role="button">Pagina Amministratore</a></RouterLink>
        </div>
      </div>

    </div>



  </div>
</template>

<script>


import axios from "axios";
import $ from 'jquery';
import Cookie from 'vue-cookies'


//import {cookieService} from "@/Service/cookieService"

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',
  data() {
    return {
      email: '',
      password: '',
      role: '',
      isLogged: false,
      altreInfo:'',
      admin:false,
      sessione:'sessione inesistente',
    
    };
  },
  created() {
    // Controlla se esiste un flag di accesso nel localStorage
    /*/!*TODO:da modificare se la sessione utente lato server è scaduta bisogna modificarla nella localStorage*!/
    const isLogged = localStorage.getItem('isLogged');
    this.isLogged = isLogged === 'true';
    const admin= localStorage.getItem('admin');
    this.admin= admin === 'true';*/
  },

  methods: {
    
    Credential()
    {
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      $.get(url,{action: 'getAdmin', email: this.email,})
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response === "admin") {
              console.log("Admin")
              this.admin = true;
              localStorage.setItem('admin', 'true');
            } else {
              console.log("NOT Admin")
              this.admin=false;
            }

          })
          .catch(error => {

            console.error(error);
          });
    },
    Click() {
      console.log('logout')
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'logout',
      };
      axios.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Logout") {
              console.log(" logout ")
              this.isLogged = false;
              localStorage.removeItem('isLogged');
              localStorage.removeItem('admin');
            } else {
              alert("failed logout ");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },
    getInfo(a){
                var self = this;
                if (self.sessione==='sessione inesistente')
                    $.post('http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet', {action: 'getSession', email:a}, function (data) {
                        self.sessione = data;
                        
                    })
                else
                    $.post(this.link, {utente: this.account, sessione: this.sessione},
                        function (data) {
                            self.altreInfo = data;
                        });
                },
    submitForm() {
      var self=this;
      //var a= this.email;
      //console.log(this.email)
      //this.getInfo(a);
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
            $.post(url,{action: 'submitLogin',email: this.email,password: this.password},
            function(response){
              console.log(response)
              if(response[0] =='isLogged' )
              {
                console.log(response.$cookies)
                console.log("data is"+response)
                self.isLogged = true;
                localStorage.setItem('isLogged',true);
                self.Credential();
                localStorage.setItem("email",self.email)
                // SETTING VUE COOCKIES
                //var expireIn = 1/1440*response[2];
                Cookie.set(self.email, response[1]);
                let string='macon@h.com'
                let risultato=string.replace(/@/g,'%40')
                console.log(risultato)
              }
              else{
                alert("password o email errati")
                this.email=""
              this.password=""
              }
            }
            ).catch(error => {
              console.error(error);
              alert("email o password errati")
              this.email=""
              this.password=""
            });
            
            //self.$cookies.set("user_session",this.email),
            //self.Credential();
    },
    submitForm1() {
      const isLogged = localStorage.getItem('isLogged');
      this.isLogged = isLogged === 'true';
      var self=this;
      var a= this.email;
      console.log(this.email)
      this.getInfo(a);
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
            fetch(url,{action: 'submitLogin',email: this.email,password: this.password},
            // function(data){
            //   console.log(data)
            //   if(data==='isLogged')
            //   {
            //     console.log("data is"+data)
            //     self.isLogged = true;
            //     localStorage.setItem('isLogged',true);
            //     self.Credential();
            //     localStorage.setItem("email",this.email)
            //     localStorage.setItem('isLogged', 'true');
            //   }
            //   else{
            //     alert("password o email errati")
            //   }
            // }
            ).then((response) => {
              console.log(response.headers.getSetCookie());
            }).catch(error => {
              console.error(error);
            });
            
            self.$cookies.set("user_session",this.email),
            self.Credential();
    },
    login1() {
                    $.post('http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet', 
                      {action: 'submitLogin',email: this.email,password: this.password}
                    ).then((response) => {
                      // Se la richiesta ha successo, imposta il cookie di sessione
                      this.setSessionCookie(response.token);
                      // Imposta la variabile isLoggedIn a true
                      this.isLoggedIn = true;
                    }, (error) => {
                      // Se la richiesta ha fallito, mostra un errore
                      console.log(error);
                    });
                  },
                  setSessionCookie(token) {
                    // Crea un nuovo cookie di sessione
                    const cookie = new Cookie({
                      name: "session_token",
                      value: token,
                      expires: new Date(new Date().getTime() + 60 * 60 * 1000), // 1 ora
                    });
                    // Imposta il cookie
                    document.cookie = cookie.toString();
                  },
    },
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.logged-container {
  padding-top: 50px;
  padding-bottom: 50px;
  border: solid orangered 2px;
  border-radius: 10px;
  margin-top: 50px;
  background-color: white;
}
</style>
