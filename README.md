# project-Tudor2208

Note:

A1: 10

A2: 10

A3: 10

D1: 10

D2: 9

D3: 8.7

-------------------

LA: 10

LP: 9,62


Assignment 2 - functionalitati:
- Compilarea solutiilor incarcate si determinarea score-ului aferent solutiei;
- Cronjob: In fiecare zi, se trimite un email catre toti utilizatorii abonati in care este specificat userul ce a rezolvat cele mai multe probleme in ziua respectiva;
- Trimitere de email-uri: Catre toti abonatii se trimite un email in momentul in care o noua problema este adaugata;

Design pattern: Observer
- In momentul in care o noua problema este adaugata, se inregistreaza de asemenea o notificare care va fi afisata pe site si se trimite un email abonatilor.

Assignment 3 - functionalitati:
- Implementare sistem de clase: profesorii pot sa isi creeze clase, iar elevii se pot alatura acestor clase folosind un id si parola;
- Profesorii pot sa asigneze teme elevilor dintr-o clasa si pot sa vada cate probleme au fost rezolvate de fiecare in parte (odata ce a expirat deadline-ul unei teme, elevii nu mai pot incarca solutii la problemele care faceau parte din acele teme pana cand profesorul nu sterge tema);
- Adminii pot sa exporteze un fisier (TXT sau XML) cu statistici referitoare la useri (numarul de useri, numarul de useri inregistrati in ultima zi / saptamana / luna, cel mai activ user din ziua generarii raportului);
- Adminii pot vizualiza numarul de useri logati la un moment dat;
- Adminii pot vedea activitatea de login / logout a tuturor utilizatorilor si pot vizualiza activitatea doar a unui anumit user;
- Validari pe partea de backend (adnotare de email, regex pentru parola, nume, prenume).

Final project - functionalitati:
- Implementarea unui sistem de challenges: Fiecare user dispune de un anumit numar de puncte. Acesta poate sa ,,provoace" un alt user sa rezolve o anumita problema, care nu a fost rezolvata inainte de niciunul dintre ei, intr-un anumit interval de timp. Cel care obtine score-ul cel mai mare va primi punctele stabilite. Top 10 utilizatori vor fi afisati intr-un leaderboard;
- Profesorii pot sa propuna probleme, iar adminii pot sa le accepte sau sa le respinga;
- Utilizatorii pot sa caute probleme dupa id / nume;
- Validari pe partea de frontend;
- Criptare parole.
