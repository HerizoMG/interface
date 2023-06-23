CREATE DATABASE projetAffectation;

CREATE TABLE employe (
     num_empl VARCHAR(4),
     civilite VARCHAR(4),
     nom VARCHAR(50),
     prenoms VARCHAR(70),
     mail VARCHAR(50),
     poste VARCHAR(20),
     lieu VARCHAR(4)
);

CREATE TABLE affectation (
     num_affect VARCHAR(4),
     num_empl VARCHAR(4),
     ancien_lieu VARCHAR(20),
     nouveau_lieu VARCHAR(20),
     date_affect DATE,
     date_priseservice DATE
);

CREATE TABLE lieu (
     id_lieu VARCHAR(4),
     design VARCHAR(50),
     province VARCHAR(50),
);
