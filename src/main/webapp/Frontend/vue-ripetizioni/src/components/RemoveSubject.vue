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
      <div class="collapse navbar-collapse" style="justify-content: space-between;" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0 w-100">
          <li class="nav-item active">
            <RouterLink to="/Admin"><a class="nav-link active" aria-current="page" href="#">Pagina Amministratore</a></RouterLink>
          </li>
          <li class="nav-item dropdown active">
            <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Docenti</a>
            <ul class="dropdown-menu">
              <li><RouterLink to="/AddProf"><a class="dropdown-item" href="#">Aggiungi Docente</a></RouterLink></li>
              <li><RouterLink to="/DelProf"><a class="dropdown-item" href="#">Elimina Docente</a></RouterLink></li>
            </ul>
          </li>
          <li class="nav-item dropdown active">
            <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Corsi</a>
            <ul class="dropdown-menu">
              <li><RouterLink to="/Addsub"><a class="dropdown-item" href="#">Aggiungi Corso</a></RouterLink></li>
              <li><RouterLink to="/RemoveSubject"><a class="dropdown-item" href="#">Elimina Corso</a></RouterLink></li>
            </ul>
          </li>
          <li class="nav-item dropdown active">
            <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Lezioni</a>
            <ul class="dropdown-menu">
              <li><RouterLink to="/AddLesson"><a class="dropdown-item" href="#">Aggiungi Lezione</a></RouterLink></li>
              <li><RouterLink to="/RemoveAvailability"><a class="dropdown-item" href="#">Elimina Lezione</a></RouterLink></li>
            </ul>
          </li>
          <li class="nav-item active">
            <RouterLink to="/PersonalPage"><a class="btn btn-success" role="button">Modalità utente</a></RouterLink>
          </li>
        </ul>
        <div class="container-right-btn d-inline-flex justify-content-end w-25">
          <RouterLink to="/">
            <a class="btn-login btn btn-primary" @click="Click" role="button">Logout</a>
          </RouterLink>
        </div>
      </div>
    </div>
  </nav>

  <div class="logged-container d-flex justify-content-center align-items-center mt-6">
    <h1 class="title-main text-center" style="font-size: 40px; margin-top: 0;">Elimina un corso selezionandolo:</h1>
  </div>

<!--  &lt;!&ndash;FORM&ndash;&gt;
  <div v-if="!isSigned" class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">Nome</label>
        <input type="text" class="form-group-orange form-control" id="name" v-model="name">

      </div>
      <div class="form-group">
        <label for="surname">Descrizione</label>
        <input type="text" class="form-group-orange form-control" id="surname" v-model="descp">
      </div>



      <button class="btn-login btn btn-primary" @click="add">Rimuovi </button>
    </form>
  </div>-->
  <div class="form-container container rounded py-4 mt-4">
    <h3>3. Seleziona lo slot orario: </h3>
    <div class="container-table">
      <table class="table table-bordered bg-light mx-1 mt-4">
        <thead>
        <tr>
          <th scope="col">Nome</th>
          <th scope="col">Descrizione</th>
          <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="subject in subjects" :key="subject.subjectID">
          <td>{{ subject.subjectName }}</td>
          <td>{{ subject.description }}</td>
          <td><a class="btn-login btn btn-primary" role="button" @click="deleteSubject(subject.subjectID)">Elimina</a></td>
        </tr>

        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import $ from 'jquery';
import {subjectsService} from "@/Service/subjectsService";
import Cookie from "vue-cookies";
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "RemoveSubject",
  data: function(){
    return{
      isSigned:false,
      /*name:'',
      descp:'',*/
      subjects:{}


    }
  },
  created: async function() {
    try {
      this.loading = true;
      let response = await subjectsService.getAllSubjects();
      this.loading = false;
      this.subjects = response;
      console.log(this.subjects);
    } catch (e) {
      console.log(e);
    }
  },
  methods:{
    Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      localStorage.removeItem('admin');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      //cookieService.delete(localStorage.getItem('email'));
      const params = {
        action: 'logout',
      };
      $.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Logout") {
              console.log(" logout ")
              this.isLogged = false;
              localStorage.removeItem('isLogged');
            } else {
              alert("failed logout ");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'RemoveSubject',
        /*name:this.name,
        descp:this.descp,*/

      };
      if(localStorage.getItem("isLogged")==='true' && localStorage.getItem('admin')==='true') {
        $.get(url, {params}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response.data === "Added") {
                console.log(" aggiunto con successo")
                this.name = '';
                this.descp = '';
              } else {
                alert("problema aggiunta materia");
              }

            })
            .catch(error => {

              console.error(error);
            });
      }
    },
    deleteSubject(id){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      /*const params = {
        action: 'deleteSubject',
        subjectId:id
      };*/
     /* if(localStorage.getItem("isLogged")=="true"&&localStorage.getItem("admin")=="true") {*/
        $.get(url, {action: 'deleteSubject',subjectId:id,token: Cookie.get(localStorage.getItem("email"))}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response === "Added") {
                console.log(" eliminato con successo")
                alert("corso eliminato con successo")
                const index = this.subjects.findIndex(subject => subject.subjectID === id);
                if (index !== -1) {
                  this.subjects.splice(index, 1); // Rimuovi la riga corrispondente dalla lista
                }

                this.teacherId = '';
              }else if(response==='invalidSession'){
                alert("sessione invalida")
                router.push("/")
              }
              else {
                alert("problema eliminazione corso");
              }

            })
            .catch(error => {

              console.error(error);
            });
      /*}*/
    }
  }
}
</script>

<style scoped>
.nav-item{
  font-weight: bold;
  font-size: 18px;
}

.navbar-nav{
  justify-content: space-evenly;
}
</style>