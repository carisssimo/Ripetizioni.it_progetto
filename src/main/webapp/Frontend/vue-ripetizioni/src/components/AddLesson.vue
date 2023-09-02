<template>
  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">
      <div style="margin-right: 25px;">
        <router-link to="/">
          <a class="btn-login btn btn-primary" @click="Click" role="button">logout</a>
        </router-link>
      </div>
    </form>
  </nav>
  <!--  navbar per la navigazione dell'amministratore loggato-->

  <ul class="title-main navbar-ripetizioni nav nav-tabs">
    <li class="nav-item">
      <RouterLink to="/Admin"><a class="nav-link " aria-current="page" href="#">Pagina Amministratore</a></RouterLink>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Docenti</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/AddProf"><a class="dropdown-item" href="#">Aggiungi Docente</a></RouterLink></li>
        <li><RouterLink to="/DelProf"><a class="dropdown-item" href="#">Elimina Docente</a></RouterLink></li>
      </ul>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Corsi</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/Addsub"><a class="dropdown-item" href="#">Aggiungi Corso</a></RouterLink></li>
        <li><RouterLink to="/RemoveSubject"><a class="dropdown-item" href="#">Elimina Corso</a></RouterLink></li>
      </ul>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle active" data-toggle="dropdown"  role="button" aria-expanded="false">Lezioni</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/AddLesson"><a class="dropdown-item" href="#">Aggiungi Lezione</a></RouterLink></li>
        <li><RouterLink to="/RemoveAvailability"><a class="dropdown-item" href="#">Elimina Lezione</a></RouterLink></li>
      </ul>
    </li>

  </ul>

  <div class="logged-container title-container container">
    <h1 class="title-main">Inserimento nuova associazione corso-docente</h1>
  </div>

  <!--FORM-->
  <div class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label >Seleziona Docente</label>
<!--        <input type="text" class="form-group-orange form-control" id="id_prof" v-model="id_prof">-->
        <select v-model="selectedTeacher">
          <option disabled value="">Seleziona un Docente</option>
          <option v-for="teacher in teachers" :key="teacher.teacherId">{{teacher.name}}</option>
        </select>
      </div>
      <div class="form-group">
        <label >Seleziona Corso</label>
<!--        <input type="text" class="form-group-orange form-control" id="id_sub" v-model="id_sub">-->
        <select v-model="selectedSubject">
          <option disabled value="">Seleziona una materia</option>
          <option v-for="subject in subjects" :key="subject.subjectID">{{subject.subjectName}}</option>
        </select>
      </div>





      <button class="btn-login btn btn-primary" @click="add">Aggiungi </button>
    </form>
  </div>


</template>

<script>
//import axios from "axios";
import $ from 'jquery';
import {subjectsService} from "@/Service/subjectsService";
import {teacherService} from "@/Service/teachersService";
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
        }) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response === "Added") {
                console.log("lezione aggiunta con successo")
                this.id_prof = '';
                this.id_sub = '';
                this.daytime = '';
              } else {
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
      $.get(url, {action: 'addAssociation', id_prof: teacherId, id_sub: subjectId}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response === "Added") {
              console.log("Associazione aggiunta con successo");
              alert("Associazione aggiunta con successo ");
              this.id_prof = '';
              this.id_sub = '';
              this.daytime = '';
            } else {
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

</style>