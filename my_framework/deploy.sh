#!/bin/bash

# Se placer automatiquement dans le dossier du script
cd "$(dirname "$0")"

NOM_SERVLET="Sprint"
DOSSIER_CLASSES="build/classes"
LIB_DIR="lib"
PROJET_DIR="/home/itu/jo/S4/Web-dyn/Sprint/Test/lib"
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"

# Vérifier que le dossier my_framework existe
if [ ! -d "my_framework" ]; then
    echo "❌ Dossier 'my_framework' introuvable dans $(pwd)"
    exit 1
fi

# Trouver tous les fichiers .java dans my_framework (et sous-dossiers)
JAVA_FILES=$(find my_framework -name "*.java")
if [ -z "$JAVA_FILES" ]; then
    echo "❌ Aucun fichier .java trouvé dans my_framework"
    exit 1
fi

echo "📁 Fichiers à compiler :"
echo "$JAVA_FILES"

# Créer le dossier des classes compilées
mkdir -p $DOSSIER_CLASSES

# Compilation
javac -cp $SERVLET_API_JAR -d $DOSSIER_CLASSES $JAVA_FILES

# Vérifier le succès de la compilation
if [ $? -ne 0 ]; then
    echo "❌ Échec de la compilation"
    exit 1
fi

# Création du JAR (inclut toute l'arborescence des packages depuis build/classes)
jar -cf $NOM_SERVLET.jar -C $DOSSIER_CLASSES/ .

# Copie vers PROJET_DIR
cp $NOM_SERVLET.jar $PROJET_DIR/

# Nettoyage (optionnel)
rm -rf $DOSSIER_CLASSES

echo "✅ $NOM_SERVLET.jar copié dans $PROJET_DIR"