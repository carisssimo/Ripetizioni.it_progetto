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

  <div class="container mt-6">
    <div class="row">
      <div class="col">
        <div class="dropdown">
          <button class="btn btn-login dropdown-toggle dropdown-btn w-100" type="button" id="dropdownMenuButtonSubjects" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Materie disponibili
          </button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButtonSubjects">
            <ul class="dropdown-list">
              <li v-for="subject in subjects" :key="subject.subjectID" class="dropdown-item">{{subject.subjectName}}</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="dropdown">
          <button class="btn btn-login dropdown-toggle dropdown-btn w-100" type="button" id="dropdownMenuButtonTeachers" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Docenti disponibili
          </button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButtonTeachers">
            <ul class="dropdown-list">
              <li v-for="teacher in teachers" :key="teacher.name" class="dropdown-item">{{teacher.name}} {{teacher.surname}}</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="dropdown">
          <button class="btn btn-login dropdown-toggle dropdown-btn w-100" type="button" id="dropdownMenuButtonSlots" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Orari disponibili
          </button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButtonSlots">
            <ul class="dropdown-list">
              <li v-for="slot in slots" :key="slot.description" class="dropdown-item">{{slot.description}}</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!--MAIN-->
  <div class="container">
    <div class="main-row row">
      <!--colonna di titolo e descrizione-->
      <div class="col">
        <div class="title-container container">
          <h1 class="title-main">Scegli la materia e il tuo Prof</h1>
          <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua
            città selezionando le date e gli orari che ti fanno più comodo. Registrati per prenotare la tua
            lezione </p>
          <RouterLink to="/login"><a class="btn-login btn btn-primary w-50" style="font-weight: bold; " role="button">Comincia ora!</a></RouterLink>
        </div>
      </div>
      <!--colonna di carosello e descrizione(sarebbe utile eliminarlo arrivati ad una certa soglia )-->
      <div class="carousel-col col ">
        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
          <div class="carousel-inner">
            <div class="image-item carousel-item active">
              <img src="../images/photo-1544456203-0af5a69f5789.webp" class="d-block w-100" alt="...">
            </div>
            <div class="image-item carousel-item">
              <img src="../images/photo-1524508762098-fd966ffb6ef9.webp" class="d-block w-100" alt="...">
            </div>
            <div class="image-item carousel-item">
              <img src="../images/premium_photo-1663013747131-1931a76aa498.webp" class="d-block w-100" alt="...">
            </div>
          </div>
          <button class="carousel-control-prev" type="button" data-target="#carouselExampleControls"
                  data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-target="#carouselExampleControls"
                  data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </button>
        </div>

      </div>
    </div>
    <!-- riga per le descrizioni varie -->

      <div class="card-row row align-items-center">
        <div class="event-col col">
          <h1>Eventi</h1>
          <p>scopri tutti gli eventi per incontrare altri studenti</p>
        </div>
        <div class="event-col col">
          <h1>Locali</h1>
          <p>scopri tutti gli eventi della nostro borgo</p>
        </div>
        <div class="event-col col">
          <h1>Partner</h1>
          <p>scopri tutti gli eventi della nostro borgo</p>
        </div>
      </div>

    <div class="container">
      <div class="row">
        <div class="image-col col">

        </div>
        <div class="col">
          <div class="title-container container">
            <h1 class="title-main">Ora disponibili anche online</h1>
            <p class="p-start">I migliori docenti della città direttamente sul tuo schermo. Prenota subito una lezione e seguila
              comodamente da casa</p>
          </div>
        </div>
      </div>
    </div>

  </div>

</template>

<script>
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";
import {slotService} from "@/Service/slotService";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Home",
  data: function (){
    return{
      loading:false,
      teachers:{},
      subjects:{},
      slots:{}
    }
  },
  created: async function(){
    try{
      this.loading=true;
      let response=await teacherService.getAllTeachers();
      let response2=await subjectsService.getAllSubjects();
      let response3=await slotService.getAllSlots();
      this.loading=false;
      this.teachers=response;
      this.subjects=response2;
      this.slots=response3;
      console.log(this.subjects);
      console.log(this.teachers);
    }catch(e){
      console.log(e);
    }
  }
}
</script>

<style>
.image-col{
  background-image: url("../images/photo-1500989145603-8e7ef71d639e.webp");
  margin-top: 54px;
  margin-bottom: 54px;
  border-radius: 20px;
  overflow: hidden;
}

.mt-6{
  margin-top: 7rem;
}

.card-row {
  border-radius: 10px;
  border: 1px solid #ccc;
  /* Altri stili opzionali */
}

.row {
  display: flex;
  flex-wrap: wrap;
  margin-right: -15px;
  margin-left: -15px;
  /* Altri stili opzionali per le righe */
}

.nav-item{
  font-weight: bold;
  font-size: 18px;
}

.dropdown-btn{
  color: white;
  font-size: 18px;
  font-weight: bold;
}

.dropdown-menu{
  width: 100%;
}

.dropdown-list{
  list-style: none;
  padding-left: 0;
}

</style>