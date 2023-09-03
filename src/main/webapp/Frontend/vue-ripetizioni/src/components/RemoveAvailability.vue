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
    <h1 class="title-main text-center" style="font-size: 40px; margin-top: 0;">Elimina un'associazione Corso-Docente selezionandolo:</h1>
  </div>

  <div class="form-container container rounded py-4 mt-4">
    <h3>Seleziona l'associazione Corso-Docente desiderata: </h3>
    <div class="container-table">
      <table class="table table-bordered bg-light mx-1 mt-4">
        <thead>
        <tr>
          <th scope="col">Professore</th>
          <th scope="col">Corso</th>
          <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="association in associations" :key="association.subjectTeacherID">
          <td>{{ printTeacherName(association.teacherID) }}</td>
          <td>{{ printSubjectName(association.subjectID) }}</td>
          <td><a class="btn-login btn btn-primary" role="button" @click="add(association.subjectTeacherID)">Elimina</a></td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

</template>

<script>
import $ from 'jquery';
import {subjectsService} from "@/Service/subjectsService";
import {associationService} from "@/Service/associationService";
import {teacherService} from "@/Service/teachersService";
import router from "@/router";
import Cookie from "vue-cookies";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "RemoveAvailability",
  data: function(){
    return{

      id_prof:'',
      id_sub:'',
      dateAv:'',
      associations:{},
      teachers:{},
      subjects:{}

    }
  },
  created: async function() {
    try {
      this.loading = true;
      let response = await associationService.getAllAssociations();
      if(response==='notAdmin'){
        alert("sessione invalida")
        router.push("/")
      }else{
        this.associations = response;
      }
      let response2=await teacherService.getAllTeachers();
      let response3=await subjectsService.getAllSubjects();

      this.loading = false;

      this.teachers=response2;
      this.subjects=response3;
      console.log(this.associations);
      console.log(this.subjects);
      console.log(this.teachers);
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
    add(AssociationId){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      if(localStorage.getItem("isLogged")==='true' && localStorage.getItem('admin')==='true') {
        $.get(url, {
          action: 'removeAssociation',
          id: AssociationId,
          token: Cookie.get(localStorage.getItem("email"))
        }) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response === "Removed") {
                console.log("associazione rimossa con successo");
                alert("associazione rimossa con successo");
                const index = this.associations.findIndex(association => association.subjectTeacherID === AssociationId);
                if (index !== -1) {
                  this.associations.splice(index, 1); // Rimuovi la riga corrispondente dalla lista
                }
                this.id_prof = '';
                this.id_sub = '';
                this.dateAv = '';
              }else if(response==='invalidSession'){
                alert("sessione invalida")
                router.push("/")
              }
              else {
                alert("problema rimozione associazione");
              }

            })
            .catch(error => {

              console.error(error);
            });
      }
    },
    printTeacherName(Id) {
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    },
    printSubjectName(Id) {
      for (let i = 0; i < this.subjects.length; i++) {
        if (this.subjects[i].subjectID === Id) {
          console.log(this.subjects[i]);
          return this.subjects[i].subjectName;
        }
      }
      return null;
    },
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