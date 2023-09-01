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
         <!-- <div class="form-group">
            <label for="exampleFormControlSelect1">Ruolo</label>
            <select id="role" v-model="role" class="form-group-orange form-control" required>
              <option>Utente</option>
              <option>Amministratore</option>
            </select>
          </div> -->

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
</template>

<script>


import axios from "axios";
import $ from 'jquery';


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
      admin:false,
    };
  },
  created() {
    // Controlla se esiste un flag di accesso nel localStorage
    /*TODO:da modificare se la sessione utente lato server è scaduta bisogna modificarla nella localStorage*/
    const isLogged = localStorage.getItem('isLogged');
    this.isLogged = isLogged === 'true';
    const admin= localStorage.getItem('admin');
    this.admin= admin === 'true';
  },

  methods: {
    
    Credential()
    {
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      /*const params = {
        action: 'getAdmin',
        email: this.email,
      };*/
      /*axios.get(url, {params}) /!*prima effettuiamo la http request async*!/*/
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
    submitForm() {
      const isLogged = localStorage.getItem('isLogged');
      this.isLogged = isLogged === 'true';
      var self=this;
      console.log(this.email)

      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
     /* let params = {
        action: 'submitLogin',
        email: this.email,
        password: this.password,
      };*/
      /*let headers = {

          'Content-Type': 'application/json;charset=UTF-8',
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods":
              "GET, POST, PATCH, PUT, DELETE, OPTIONS",
          "Access-Control-Allow-Headers":
              "Origin, Content-Type, X-Auth-Token",
          'content-type': 'application/x-www-form-urlencoded'

      };*/

     

      /*axios.post(url, {params},axiosConfig)*/ /*prima effettuiamo la http request async*!*/
      /*axios.post("https://prova1-cc17e-default-rtdb.europe-west1.firebasedatabase.app/",params)*/
      /*axios(url, {
        method: "post",
        data:{
          action: 'submitLogin',
          email: this.email,
          password: this.password,
        }
      },headers)
          .then(response => {         /!*solo una volta eseguita la request passiamo a gestire la risposta*!/
            if (response.data === "isLogged") {
              console.log(" loggato con successo")
              this.Credential();
              this.isLogged = true;

              // Salva il flag di accesso nel localStorage
              localStorage.setItem('isLogged', 'true');
            } else {
              alert("Email o password errati ");
            }

          })
          .catch(error => {

            console.error(error);
          });*/

            $.post(url,{action: 'submitLogin',email: this.email,password: this.password},
            function(data){
              console.log(data)
              if(data==='isLogged')
              {
                self.isLogged = true;
                self.Credential();
                console.log("the variable value is "+this.isLogged)
                localStorage.setItem("email",this.email)
                localStorage.setItem('isLogged', 'true');
              }
              else{
                alert("password o email errati")
              }
            }
            ).catch(error => {
              console.error(error);
            });
            
            self.$cookies.set("user_session","25j_7Sl6xDq2Kc3ym0fmrSSk2xV2XkUkX"),
            self.Credential();
            
     /* fetch(url, {
        method: "POST",
        headers: headers,
        body:  JSON.stringify(params)
      })
          .then(function(response){
            return response.json();
          })
          .then(function(data){
            console.log(data)
          });*/

    }
  }
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
