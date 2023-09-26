# Ripetizioni.it_progetto
Progetto web app
Applicazione java web che gestisce prenotazioni di ripetizioni online essendo dotata solo di interfaccia utente per browser web.

L’applicazione è basata su architettura **MVC, con Controller + viste e Model. Si noti che non c'è comunicazione diretta tra viste e model: ogni tipo di comunicazione tra questi due livelli deve essere mediato da un controller.
Vengono gestite le **sessioni utente.
L’applicazione salva in un database relazionale a scelta i seguenti tipi di informazione:
 - account, password e ruolo degli utenti registrati;
 - titolo dei corsi di cui si offrono le ripetizioni;
 - nome e cognome dei docenti che tengono le ripetizioni;
 - associazioni corso-docente;
 - prenotazioni di ripetizioni;

L’applicazione controlla l’inserimento di input utente sia lato client che lato server per evitare che l’utente inserisca dati parziali o errati nei form (per esempio, per evitare che l’utente cerchi di collegarsi senza inserire login e password).
L’applicazione controlla sia lato client che lato server che gli utenti non eseguano operazioni illecite. Per es., gli utenti non autenticati possono vedere il catalogo delle ripetizioni disponibili, ma non possono segnare come effettuate, o disdire, le prenotazioni; solo gli amministratori devono poter operare sul catalogo per inserire/rimuovere corsi e docenti; ogni utente (tranne l’amministratore) deve poter vedere solo le proprie ripetizioni e non quelle altrui.


L’interfaccia è implementata come una sola Single Page Application utilizzando **Vue.js e HTML5; Il layout delle pagine dell’interfaccia utente è specificato con CSS ( utilizzando **Bootstrap) ed è fluido.
Il controllo dell’input utente lato client è effettuato utilizzando i tag di HTML5 e/o JavaScript.
Il backend dell’applicazione è implementato in **Java(servlet).

