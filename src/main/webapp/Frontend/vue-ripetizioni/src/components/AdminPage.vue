<template>

  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">
      <div style="margin-right: 25px;">
        <RouterLink to="/">
          <a class="btn-login btn btn-primary" @click="Click" role="button">logout</a>
        </RouterLink>
      </div>
    </form>
  </nav>
  <div class="logged-container title-container container">
    <h1 class="title-main">Pagina Amministratore</h1>
    <h1 class="title-main">Bentornato!</h1>
    <span style="padding: 0 10px;"></span>
  <div class="card-row row align-items-center mt-4">
    <div class="event-col col">
      <h1>Docenti</h1>
      <p>Aggiungi o rimuovi i docenti </p>
    </div>
    <div class="event-col col">
      <h1>Corsi</h1>
      <p>Aggiungi o rimuovi corsi</p>
    </div>
    <div class="event-col col">
      <h1>Lezioni</h1>
      <p>Aggiungi o rimuovi lezioni</p>
    </div>
  </div>



    <p class="p-start"></p>
    <div class="row">
      <div v-for="(button, index) in buttons" :key="index" class="col-md-4">
        <div class="md-4">
          <RouterLink :to="button.to">
            <a :class="[button.classes, 'custom-button']" role="button">
            <span class="vertical-center">{{ button.label }}
           </span>
            </a>
          </RouterLink>
        </div>
        <span style="padding: 0 10px;"></span>
      </div>
    </div>
    <!--
    <div class="row">
      <div class="col-md-4">
        <p>
          <button class="btn-login btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseProfessors" aria-expanded="false" aria-controls="collapseWidthExample">
            Insegnanti disponibili
          </button>
        </p>
        <div style="min-height: 120px;">
          <div class="collapse width" id="collapseProfessors">
            <div class="list-group">
              <a v-for="teacher in teachers" :key="teacher.name" class="list-group-item list-group-item-action">{{teacher.name}} {{teacher.surname}}</a>
            </div>
          </div>
        </div>
      </div>
      <div class="col">
        <p>
          <button class="btn-login btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseCourses" aria-expanded="false" aria-controls="collapseWidthExample">
            Materie disponibili
          </button>
        </p>
        <div style="min-height: 120px;">
          <div class="collapse width" id="collapseCourses">
            <div class="card card-body" style="width: 320px;">
              <ul class="list-group">
                <li v-for="subject in subjects" :key="subject.subjectID" class="list-group-item">{{subject.subjectName}}</li>

              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>-->
    <!--
    <RouterLink to="/AddProf">
      <a class="btn-login btn btn-primary" role="button">+ Nuovo Docente</a>
    </RouterLink>
    <span style="padding: 0 10px;"></span>
    <RouterLink to="/Addsub">
      <a class="btn-l btn-login btn btn-success" role="button">+ Nuovo Corso</a>
    </RouterLink>
    <span style="padding: 0 10px;"></span>
    <RouterLink to="/AddLesson">
      <a class="btn-l btn-login btn btn-success" role="button">+ Nuova Lezione</a>
    </RouterLink>
    <span style="padding: 0 10px;"></span>
    <RouterLink to="/DelProf">
      <a class="btn-l btn-login btn btn-success" role="button">- Elimina Docente</a>
    </RouterLink>
    <span style="padding: 0 10px;"></span>
    <RouterLink to="/RemoveSubject">
      <a class="btn-l btn-login btn btn-success" role="button">- Elimina Corso</a>
    </RouterLink>
    <span style="padding: 0 10px;"></span>
    <RouterLink to="/RemoveAvailability">
      <a class="btn-l btn-login btn btn-success" role="button">- Elimina Lezione</a>
    </RouterLink>-->

      <h1>Ecco le lezioni in corso</h1>

    <div class="form-container-selector container">
      <table class="table">
        <thead>
        <tr>
          <th scope="col">Docente</th>
          <th scope="col">Corso</th>
          <th scope="col">Slot Orario</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="availability in availabilities" :key="availability.availabilityID">
          <td>{{ printTeacherName(availability.teacherId) }}</td>
          <td>{{ printSubjectName(availability.subjectId) }}</td>
          <td>{{ availability.dayTime }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <span style="padding: 0 10px;"></span>
    <div class="button-container container align-items-center">
      <RouterLink to="/Login"><a class="btn-l btn-login btn btn-success" role="button">Torna indietro</a></RouterLink>
    </div>

  </div>



</template>

<script>
import { teacherService } from "@/Service/teachersService";
import { subjectsService } from "@/Service/subjectsService";
import { availabilityService } from "@/Service/availabilityService";
import axios from "axios";

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
  created: async function() {
    try {
      this.loading = true;
      let response = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await availabilityService.getAllAvailabilitiesAvailable();
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
  },

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
      axios.get(url, {params}) /*prima effettuiamo la http request async*/
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

      axios
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
</style>