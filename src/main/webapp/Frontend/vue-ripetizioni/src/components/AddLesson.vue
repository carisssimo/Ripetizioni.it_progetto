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
    <h1 class="title-main text-center" style="font-size: 40px; margin-top: 0;">Inserimento nuova associazione corso-docente:</h1>
  </div>

  <div class="form-container container rounded py-4 mt-4">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <h3>1. Seleziona Docente:</h3>

        <select v-model="selectedTeacher" class="w-75 mt-3 selection-option">
          <option disabled value=""></option>
          <option v-for="teacher in teachers" :key="teacher.teacherId">{{teacher.name}}</option>
        </select>
      </div>
      <div class="form-group">
        <h3>2. Seleziona Corso: </h3>

        <select v-model="selectedSubject" class="w-75 mt-3 selection-option">
          <option disabled value=""></option>
          <option v-for="subject in subjects" :key="subject.subjectID">{{subject.subjectName}}</option>
        </select>
      </div>

      <button class="btn-login btn btn-primary mt-4" @click="add">Aggiungi </button>
    </form>
  </div>

  <!--div class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label >Seleziona Docente</label>

        <select v-model="selectedTeacher">
          <option disabled value="">Seleziona un Docente</option>
          <option v-for="teacher in teachers" :key="teacher.teacherId">{{teacher.name}}</option>
        </select>
      </div>
      <div class="form-group">
        <label >Seleziona Corso</label>

        <select v-model="selectedSubject">
          <option disabled value="">Seleziona una materia</option>
          <option v-for="subject in subjects" :key="subject.subjectID">{{subject.subjectName}}</option>
        </select>
      </div>

      <button class="btn-login btn btn-primary" @click="add">Aggiungi </button>
    </form>
  </div-->

</template>

<script>
//import axios from "axios";
import $ from 'jquery';
import {subjectsService} from "@/Service/subjectsService";
import {teacherService} from "@/Service/teachersService";
import Cookie from "vue-cookies";
import router from "@/router";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "AddLesson",
  data: function(){
    return{
      id_prof:'',
      id_sub:'',
      daytime:'',
      selectedTeacher:'',
      selectedSubject:'',
      subjects:{},
      teachers:{}
    }
  },
  created: async function(){
    try {
      let response = await subjectsService.getAllSubjects();
      let response2=await teacherService.getAllTeachers();
      this.subjects = response;
      this.teachers=response2;
      console.log(this.subjects);
    }catch (e) {
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
     /* const params = {
        action: 'addLesson',
        id_prof:this.id_prof,
        id_sub:this.id_sub,
        daytime:this.daytime,

      };*/
      if(localStorage.getItem("isLogged")==='true' && localStorage.getItem('admin')==='true') {
        $.get(url, {
          action: 'addLesson',
          id_prof: this.id_prof,
          id_sub: this.id_sub,
          daytime: this.daytime,
          token: Cookie.get(localStorage.getItem("email"))
        }) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response === "Added") {
                console.log("lezione aggiunta con successo")
                this.id_prof = '';
                this.id_sub = '';
                this.daytime = '';
              } else if(response==='invalidSession'){
                alert("sessione invalida")
                router.push("/")
              }
              else {
                alert("problema aggiunta lezione");
              }

            })
            .catch(error => {

              console.error(error);
            });
      }
    },
    async onSubmit() {
      console.log(this.selectedTeacher);
      console.log(this.selectedSubject);
      let teacherId = await teacherService.getTeacherId(this.selectedTeacher);
      let subjectId = await subjectsService.getSubjectId(this.selectedSubject);
      console.log(teacherId);
      console.log(subjectId)

      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      $.get(url, {action: 'addAssociation', id_prof: teacherId, id_sub: subjectId, token: Cookie.get(localStorage.getItem("email"))}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response === "Added") {
              console.log("Associazione aggiunta con successo");
              alert("Associazione aggiunta con successo ");
              this.id_prof = '';
              this.id_sub = '';
              this.daytime = '';
            } else if(response==='invalidSession'){
              alert("sessione invalida")
              router.push("/")
            }
            else {
              alert("problema aggiunta associazione: l'associazione potrebbe essere già presente nel database");
            }

          })
          .catch(error => {

            console.error(error);
          });
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

.container-btn-group{
  display: inline-block;
  float: right;
}

.selection-option{
  height: 2rem;
}
</style>