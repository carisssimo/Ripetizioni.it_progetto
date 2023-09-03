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
        </ul>
        <div class="container-right-btn d-inline-flex justify-content-end w-75">
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
    <div class="container-btn-group">
      <div class="btn-group btn-group-justified" role="group">
        <div class="btn-group" role="group">
          <RouterLink :to="'/'+this.selected">
            <a class="btn-l btn-login btn btn-success" role="button">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
              </svg>
            </a>
          </RouterLink>
        </div>
        <div class="btn-group" role="group">
          <RouterLink :to="'/'+this.selected">
            <a class="btn-l btn-login btn btn-success" role="button">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-right" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
              </svg>
            </a>
          </RouterLink>
        </div>
      </div>
    </div>
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
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'RemoveSubject',
        /*name:this.name,
        descp:this.descp,*/

      };
      $.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Added") {
              console.log(" aggiunto con successo")
              this.name='';
              this.descp='';
            } else {
              alert("problema aggiunta materia");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },
    deleteSubject(id){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      /*const params = {
        action: 'deleteSubject',
        subjectId:id
      };*/
     /* if(localStorage.getItem("isLogged")=="true"&&localStorage.getItem("admin")=="true") {*/
        $.get(url, {action: 'deleteSubject',subjectId:id}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response === "Added") {
                console.log(" eliminato con successo")
                const index = this.subjects.findIndex(subject => subject.subjectID === id);
                if (index !== -1) {
                  this.subjects.splice(index, 1); // Rimuovi la riga corrispondente dalla lista
                }

                this.teacherId = '';
              } else {
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