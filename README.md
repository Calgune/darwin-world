# Darwin World

Symulacja ewolucji zwierząt na mapie, napisana w Javie z interfejsem w JavaFX. Zwierzaki chodzą po planszy, szukają trawy, rozmnażają się i przekazują dalej swoje geny (z drobnymi mutacjami). Można patrzeć, jak populacja się zmienia z dnia na dzień, i sprawdzać, które strategie przeżywają.

Projekt wykonany na przedmiocie **Programowanie obiektowe** (AGH, Informatyka).

## Autorzy

- Paweł Pochwalski
- Justyna Konior

## Film

Krótki film pokazujący, jak działa aplikacja:



https://github.com/user-attachments/assets/303ab0a5-631a-4d8a-bb95-317e23c03dc4



## Jak działa symulacja

Świat to plansza w kształcie walca: lewa i prawa krawędź się łączą, góra i dół są ścianami. Środkowy pas mapy to dżungla, gdzie trawa rośnie częściej, reszta to stepy.

Każdy dzień wygląda mniej więcej tak:

1. Zwierzęta tracą energię (dzienny koszt życia).
2. Każde rusza się zgodnie ze swoim genomem.
3. Zwierzęta stojące na trawie ją zjadają i odzyskują energię.
4. Zwierzęta z odpowiednio dużą ilością energii się rozmnażają. Dziecko dostaje mieszankę genów rodziców, a część genów może zmutować.
5. Na mapie wyrasta nowa trawa.

Martwe zwierzaki znikają z planszy, ale ich długość życia zostaje wliczona do statystyk.

### Tryby

- **Normalny**: stałe warunki przez cały czas.
- **Sezonowy**: na zmianę lato i zima. Zimą spada temperatura, a zwierzęta muszą radzić sobie z większym kosztem życia. Długość pór roku, minimalną temperaturę i odległość, na jaką rozchodzi się ciepło, ustawia się w konfiguracji.

## Co można w programie robić

- ustawić wszystkie parametry symulacji (rozmiar mapy, liczba zwierząt i traw, energie, koszty rozmnażania, długość genomu, zakres mutacji i inne),
- skorzystać z gotowych presetów: *Bezpłodni*, *Indie*, *Postapo*,
- zapisywać i wczytywać konfigurację z pliku `.txt`,
- uruchamiać kilka symulacji naraz, każda w osobnym oknie,
- zatrzymywać i wznawiać symulację,
- oglądać statystyki na bieżąco: liczba zwierząt i traw, puste pola, najpopularniejszy genotyp, średnia energia, średnia długość życia i średnia liczba dzieci,
- przeglądać wykresy tych wartości w czasie (w trybie sezonowym także temperaturę),
- po zatrzymaniu kliknąć w zwierzaka i śledzić jego genom, energię, wiek, liczbę dzieci, potomków i zjedzonych roślin,
- zapisywać statystyki z każdego dnia do pliku CSV.

## Uruchomienie

Potrzebne jest **JDK 25** (Gradle w razie czego sam spróbuje je dobrać). Biblioteki, w tym JavaFX i OpenCSV, pobierają się automatycznie.

```bash
git clone https://github.com/Calgune/Darwin-World.git
cd Darwin-World/oolab
./gradlew run
```

Na Windowsie zamiast `./gradlew` używamy `gradlew.bat`.

Można też otworzyć projekt w IntelliJ IDEA, poczekać aż Gradle się zsynchronizuje i uruchomić zadanie `application > run`.

Jeśli IntelliJ nie chce odpalić aplikacji z zielonej strzałki, w ustawieniach (`Build Tools > Gradle`) trzeba ustawić *Build and run using: Gradle*.

## Technologie

- Java 25
- JavaFX 25
- Gradle
- OpenCSV
- JUnit 5

## Struktura projektu

Kod jest podzielony na pakiety w `agh.ics.oop`:

- `model` – zwierzęta, trawa, genomy, mapa i jej ustawienia,
- `model.maps` – implementacje mapy,
- `model.simulation` – silnik i logika symulacji (normalnej i sezonowej),
- `model.observers` – obserwatorzy zmian na mapie i w statystykach,
- `model.util` – klasy pomocnicze, m.in. zapis do CSV i obsługa plików konfiguracyjnych,
- `presenter` – kontrolery widoków JavaFX.

Widoki są opisane w plikach FXML w `src/main/resources`.
