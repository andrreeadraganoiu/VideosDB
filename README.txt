322CD Draganoiu Andreea

link github : https://github.com/andreea-00/Tema1_POO.git

Main :  Se gasesc arraylist-uri pentru filme, seriale, useri si actori.
	Fiecare entitate din array este o clasa noua -Movie, Serial, User,
	Actor- care se afla in compunere cu clasele primite default -MovieInputData,
	SerialInputData, UserInputData si ActorInputData pentru a nu
	altera parsarea datelor din aceste clase. Se apeleaza fiecare
	actiune in clasa Action.

Comenzi : 

1.Favorite - Adauga un video in lista de favorite a unui user. 
	     Gasesc userul dupa numele din action in lista cu toti userii folosind 
	     functia implementata in Utils. Verific mai intai in istoricul din HashMap
 	     daca a fost vazut. Daca nu a fost vazut sau daca a fost deja adaugat in 
	     lista de favorite nu mai poate fi adaugat in lista de favorite a acelui
	     user si se da return.

2.View     - Marcheaza un film/serial ca vazut. Daca video-ul nu a mai vazut de acel 
	     user se adauga un nou element in HashMap cu valoarea 1, altfel valoarea
	     corespunzatoare filmului/serialului se incrememteaza cu 1.

3.Rating   - Ofera rating unui videoclip vazut. Sunt folosite functiile implementate
	     in Utils pentru a obtine din lista de input userul, filmul respectiv 
	     serialul folosind username-ul userului, titlul filmului sau serialului.
	     Se verifica mai intai daca filmul/ sezonul din serial se afla in istoric 
	     ca fiind vazut sau daca a mai primit rating de la acest user si se da return.
             In acest sens exista in Movie si Season o lista cu evidenta tutoror userilor 
	     care au dat rating la acest film/sezon din serial. Reciproc, in User se afla 
	     campul numberOfRatings pentru a retine numarul totale de ratinguri oferite
	     de un anumit user. Acest camp este folosit pentru sortarea userilor in query.

Queries :

Pentru actori - query/ActorQuery

1.Average - Sorteaza primii n actori in functie de media ratingurilor primite la filmele/
            serialele in care au jucat. Video-urile cu rating 0 nu se iau in considerare la
	    medie. 
	    Se pun intr-o lista noua toti actorii cu rating diferit de 0. 
	    Pentru sortare este suprascris in clasa Actor un comparator pentru
	    ratingul per actor si pentru nume. La final pentru a afisa cei n actori
	    ceruti sunt scosi de la finalul listei cei nr_total_actori - n.

2.Awards  - Sorteaza primii n actori care contin premiile mentionate dupa numarul lor
	    total de premii. Este implementat in Actor un comparator pentru numarul 
	    total de premii si pentru nume.La final pentru a afisa cei n actori
	    ceruti sunt scosi din lista cei nr_total_actori - n.

3.Filter Description - Sorteaza primii n actori in descrierea carora se gasesc toate
		       cuvintele mentionate. Este implementat in Actor un comparator pentru
		       sortarea numelor alfabetic.La final pentru a afisa cei n actori
	    	       ceruti sunt scosi din lista cei nr_total_actori - n.

Pentru video-uri - query/VideoQuery

1.Rating - Afiseaza primele n videoclipuri sortate dupa media ratingurilor primite.
	   Se pun intr-o lista noua toti actorii care contin anul sau unul din genurile
	   date ca input in action. Apoi se sorteaza similar ca inainte folosind 
	   comparatori implementati in clasele Movie si Serial.

2.Favorite - Primele n video-uri sortate dupa numarul de aparitii in listele de video-uri
             favorite ale utilizatorilor. Se incrementeaza campul NoFavoriteAdd din Movie
	     si Serial de fiecare data cand acel film/serial este gasit in lista de favorite
	     al unui utilizator. Se sorteaza similar dupa acest camp si dupa nume.

3.Longest - Afiseaza primele n cele mai lungi videoclipuri. Se foloseste campul duration
	    primit deja in MovieInputData, iar pentru seriale se calculeaza durata tuturor
	    sezoanelor. Se sorteaza dupa acest camp si dupa nume.

4.Most viewed - Primele n videoclipuri dupa numarul total de vizualizari. Se parcurge 
		istoricul fiecarui user si pentru fiecare film/serial in parte se aduna
		numarul de vizualizari care ii apartin din dreptul fiecarui user. Se 
		foloseste campul noViews din Movie si Serial.Se sorteaza dupa acest camp si 
		dupa nume.

Pentru useri - query/UserQuery

1.Number of ratings - Primii n useri care au dat cele mai multe ratinguri. Se foloseste
		      campul numberOfRatings din clasa User care a fost actualizat de 
		      fiecare data cand se face o comanda de rating.Se sorteaza dupa acest 
		      camp si dupa nume.

Recomandari :

Pentru toti utilizatorii - recommendations/Basic

1.Standard - Intoarce primul videoclip nevazut de user din baza de date.
	     Se parcurge mai intai lista de filme iar apoi cea de seriale si se 
	     afiseaza primul videclip care nu se afla in istoricul userului. Daca le-a
	     vazut pe toate nu se poate aplica recomandarea.

2.Best unseen - Intoarce primul videoclip cu cel mai bun rating si nevazut de user 
		din baza de date. Se foloseste un maxim pentru rating si se parcurge
		lista cu filme/seriale pentru a gasi video-ul cu cel mai mare rating
		si nevazut de user.
		Daca doua videclipuri au acelasi maxim se pastreaza primul gasit din
		lista de input.

Pentru utilizatoare premium - recommendations/Premium

1.Popular - Intoarce primul video nevizualizat din cel mai popular gen.
	    Se pun toate genurile intalnite in video-uri intr-un HashMap
	    avand ca valoare numarul de filme/seriale care se incadreaza in acest
	    gen.Se face sortarea dupa cheie cu ajutorul unui comparator iar rezultatul
	    sortarii se pune intr-un LinkedHashMap pentru a se pastra ordinea. Se
	    parcurge HashMap-ul cu genuri si se intoarce primul videoclip din ordinea 
	    genurilor care nu a mai fost vazut de user.

2.Favorite - Intoarce primul videoclip care e cel mai des intalnit in lista de favorite
	     a userilor. Se recalculeaza noFavoriteAdd si similar se foloseste un maxim
	     pentru a se gasi primul videclip cu cele mai multe adaugari la favorite.

3.Search - Se afiseaza toate videclipuri nevazute de user dintr-un anumit gen sortate 
           cresc dupa rating. Se creeaza o noua lista cu filmele respectiv serialele care 
	   contin acel gen folosind functia de filtrare implementata in VideoQuery.
	   Numele alaturi de rating se pun intr-un HashMap si se foloseste un comparator
	   care sorteaza crescaror dupa rating si alfabetic.  