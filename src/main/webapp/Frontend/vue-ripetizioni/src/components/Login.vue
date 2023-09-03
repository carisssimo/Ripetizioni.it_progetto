<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-lg bg-white rounded fixed-top">
    <div class="container-fluid">
      <div class="container-logo-brand align-content-center">
        <img class="align-content-center" src="../assets/logo-fin.png" height="50" width="50">
        <a class="navbar-brand" style="font-weight: bold; color: orangered; font-size: 30px" href="#">Ripe.it</a>
      </div>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item mx-3">
            <RouterLink to="/"><a class="nav-link active pe-auto" aria-current="page">Home</a></RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container-full-screen">
    <div class="row w-100">
      <div class="col pt-6 justify-content-center">
        <h1 style="text-align: center; font-size: 60px; font-weight:bold; padding-top: 1rem;">Accedi alla tua<br>Area Personale</h1>
        <h4 style="text-align: center; margin-top: 2rem; margin-bottom: 1rem">Accedi con il tuo account per utilizzare i nostri servizi!</h4>
        <img src="../images/img_hero.png" alt="">
      </div>
      <div class="col align-self-center px-5">
        <div v-if="!isLogged">
          <div class="form-container container align-items-center py-4">
            <form v-on:submit.prevent="submitForm">
              <div class=" form-group">
                <label for="email">Indirizzo email</label>
                <input id="email" v-model="email" aria-describedby="emailHelp" class="form-group-orange form-control"
                       required type="email" autocomplete="family-name" placeholder="Inserisci la tua email:">

              </div>
              <div class="form-group">
                <label for="password">Password</label>
                <input id="password" v-model="password" class="form-group-orange form-control" required type="password" placeholder="Inserisci la tua password:">
              </div>
              <!-- <div class="form-group">
                 <label for="exampleFormControlSelect1">Ruolo</label>
                 <select id="role" v-model="role" class="form-group-orange form-control" required>
                   <option>Utente</option>
                   <option>Amministratore</option>
                 </select>
               </div> -->

              <div class="form-group">
                <button class="btn-login btn btn-primary" >Accedi</button>
              </div>
              <RouterLink to="/signin"><a style="color: orangered">Non hai un account? <strong>Clicca qui per registrarti</strong>!</a></RouterLink>
            </form>
          </div>
        </div>

        <div v-if="isLogged">
          <div class="logged-container title-container container">
            <h1 class="title-main">Bentornato!</h1>
            <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua
              città selezionando le date e gli orari che ti fanno più comodo.Prenota la tua
              lezione oppure dai un'occhiata alle lezioni già prenotate!!</p>
            <!--          <RouterLink to="/smartBooking"><a class="btn-login btn btn-primary" role="button">Prenota una lezione</a></RouterLink>
                    <span style="padding: 0 10px;"></span>-->
            <RouterLink to="/PersonalPage"><a class="btn-l btn-login btn btn-success" role="button">Inizia</a></RouterLink>
            <span style="padding: 0 10px;"></span>
            <!--        <RouterLink to="/"><a class="btn-l btn-login btn btn-success" role="button" >Torna alla home</a></RouterLink>
                    <span style="padding: 0 10px;"></span>
                    <RouterLink to="/update"><a class="btn-l btn-login btn btn-success" role="button" >Aggiorna la tua Ripe</a></RouterLink>-->
            <div v-if="admin">
              <div :style="{ paddingTop: '20px', paddingBottom: '20px' }"></div>
              <RouterLink to="/Admin"><a class="btn-l btn-login btn btn-success" role="button">Pagina Amministratore</a></RouterLink>
            </div>
          </div>

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
                Cookie.set(self.email, response[1],response[2]);
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

.pt-6{
  padding-top: 6rem;
}

.container-full-screen{
  background-image: url("../images/bubbles.png");
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
}

.container-full-screen img{
  width: 100%;
  height: 80%;
  margin-top: -6rem;
  padding-left: 5rem;
}
</style>
