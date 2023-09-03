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
    <h1 class="title-main text-center" style="font-size: 40px; margin-top: 0;">Riepilogo e storico delle ripetizioni</h1>
  </div>

  <router-view></router-view>
<!--    <div class="form-container-selector container">
      <table class="table">
        <thead>
        <tr>
          <th scope="col">Docente</th>
          <th scope="col">Corso</th>
          <th scope="col">Slot Orario</th>
          <th scope="col">Utente</th>
          <th scope="col">Stato</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="availability in availabilities" :key="availability.availabilityID">
          <td>{{ printTeacherName(availability.teacherId) }}</td>
          <td>{{ printSubjectName(availability.subjectId) }}</td>
          <td>{{ availability.dayTime }}</td>
          <td>{{ availability.userId}}</td>
          <td>{{ availability.booking}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <span style="padding: 0 10px;"></span>
    <div class="button-container container align-items-center">
      <RouterLink to="/Login"><a class="btn-l btn-login btn btn-success" role="button">Torna indietro</a></RouterLink>
    </div>-->





</template>

<script>
/*import { teacherService } from "@/Service/teachersService";
import { subjectsService } from "@/Service/subjectsService";
import { availabilityService } from "@/Service/availabilityService";*/
import $ from 'jquery';

export default {
  name: "AdminPage",
  data() {
    return {
      loading: false,
      teachers: {},
      subjects: {},
      availabilities: {},
      teacherSelected: this.teacherSelected,
      subjectSelected: this.subjectSelected,
      imageSrc: '/Users/lorenzobettiol/IdeaProjects/Ripetizioni.it_progetto/src/main/webapp/Frontend/vue-ripetizioni/src/images/1600x480-img_3998-v3_348114_0.webp',
      altText: 'Descrizione dell\'immagine',
      buttons: [
        {
          label: "+ Nuovo Docente",
          classes: "btn-login btn btn-primary",
          to: "/AddProf",
        },
        {
          label: "+ Nuovo Corso",
          classes: "btn-l btn-login btn btn-success",
          to: "/Addsub",
        },
        {
          label: "+ Nuova Lezione",
          classes: "btn-l btn-login btn btn-success",
          to: "/AddLesson",
        },
        {
          label: "- Elimina Docente",
          classes: "btn-l btn-login btn btn-success",
          to: "/DelProf",
        },
        {
          label: "- Elimina Corso",
          classes: "btn-l btn-login btn btn-success",
          to: "/RemoveSubject",
        },
        {
          label: "- Elimina Lezione",
          classes: "btn-l btn-login btn btn-success",
          to: "/RemoveAvailability",
        },
      ],
    };
  },
  /*created: async function() {
    try {
      this.loading = true;
      let response = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await availabilityService.getAllAvailabilities();
      this.loading = false;
      this.teachers = response.data;
      this.subjects = response2.data;
      this.availabilities = response3.data;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);
    } catch (e) {
      console.log(e);
    }
  },*/

  methods: {
    printTeacherName(Id) {
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    },
    Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      localStorage.removeItem('admin');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
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

    printSubjectName(Id) {
      for (let i = 0; i < this.subjects.length; i++) {
        if (this.subjects[i].subjectID === Id) {
          console.log(this.subjects[i]);
          return this.subjects[i].subjectName;
        }
      }
      return null;
    },
    booking(id) {
      console.log(id);
      const url = "http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet";
      const params = {
        action: "bookingAvailability",
        availabilityId: id
      };

      $
          .get(url, { params })
          .then(response => {
            if (response.data === "booked") {
              console.log("Prenotato con successo");
              const index = this.availabilities.findIndex(availability => availability.availabilityID === id);
              if (index !== -1) {
                this.availabilities.splice(index, 1); // Rimuovi la riga corrispondente dalla lista delle disponibilità
              }
            } else {
              alert("Prenotazione fallita");
            }
          })
          .catch(error => {
            console.error(error);
          });
    }
  }
};
</script>


<style scoped>

  .custom-button {
    width: 200px; /* Imposta la dimensione fissa desiderata */
    height: 40px; /* Imposta la dimensione fissa desiderata */
  }
  .vertical-center {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }
  .btn-login
  {
    width: 200px;
  }

  .nav-item{
    font-weight: bold;
    font-size: 18px;
  }

  .navbar-nav{
    justify-content: space-evenly;
  }
</style>