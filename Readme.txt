Para el login de la base de datos usé una variable de entorno global en mi máquina (uso vscode y asi logré usar variables de entorno), 
applications.properties debe ser modificado para conectar correctamente a una base de datos.

Nota sobre la implementación de CREATE CATEGORY:
Como el código de sql especifíca que tanto category como acronym tienen que ser únicos, pero no se define puntualmente como manejar
todos los casos de colisión en la especificación de los endpoints, se decidió que funciona de la siguiente manera:
Para activar una categoria, se necesitan dar tanto el category como acronym correctos, de sólo dar uno correcto, se responde bad request.

Hizé un rebase para corregir un error de dedo en el mensaje del primer commit, y haré uno para mezclar esta edición del readme con el commit de práctica 4, por lo que todos los commits aparecen como hechos recientemente.
