"use client"

import { useState, useEffect } from "react"
import { Card, CardContent, CardHeader, CardTitle, CardFooter } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Checkbox } from "@/components/ui/checkbox"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Search, Save, FileText, Clock, User, School, BookOpen } from "lucide-react"
import { useToast } from "@/hooks/use-toast"

// Données statiques pour le tableau de bord du surveillant
const MOCK_DATA = {
  surveillant: {
    id: 1,
    nom: "Benali",
    prenom: "Mohammed",
    matricule: "SURV-2023-001",
    email: "benali.mohammed@universite.ma",
  },
  salleInfo: {
    id: 1,
    nom: "Salle A101",
    capacite: 40,
    batiment: "Bâtiment A",
    etage: "1er étage",
  },
  examenInfo: {
    id: 1,
    matiere: {
      id: 1,
      nom: "Mathématiques Avancées",
      code: "MATH301",
    },
    date: "2023-06-15",
    heureDebut: "09:00",
    heureFin: "11:00",
    duree: 120,
  },
  etudiants: [
    {
      id: 1,
      matricule: "ETU-2023-001",
      nom: "Alaoui",
      prenom: "Youssef",
      email: "alaoui.youssef@etudiant.ma",
      place: "A1",
      present: true,
    },
    {
      id: 2,
      matricule: "ETU-2023-002",
      nom: "Berrada",
      prenom: "Fatima",
      email: "berrada.fatima@etudiant.ma",
      place: "A2",
      present: false,
    },
    {
      id: 3,
      matricule: "ETU-2023-003",
      nom: "Chaoui",
      prenom: "Ahmed",
      email: "chaoui.ahmed@etudiant.ma",
      place: "A3",
      present: true,
    },
    {
      id: 4,
      matricule: "ETU-2023-004",
      nom: "Doukkali",
      prenom: "Samira",
      email: "doukkali.samira@etudiant.ma",
      place: "A4",
      present: true,
    },
    {
      id: 5,
      matricule: "ETU-2023-005",
      nom: "El Fassi",
      prenom: "Karim",
      email: "elfassi.karim@etudiant.ma",
      place: "A5",
      present: false,
    },
    {
      id: 6,
      matricule: "ETU-2023-006",
      nom: "Fathi",
      prenom: "Nadia",
      email: "fathi.nadia@etudiant.ma",
      place: "A6",
      present: true,
    },
    {
      id: 7,
      matricule: "ETU-2023-007",
      nom: "Ghali",
      prenom: "Hassan",
      email: "ghali.hassan@etudiant.ma",
      place: "A7",
      present: true,
    },
    {
      id: 8,
      matricule: "ETU-2023-008",
      nom: "Haddad",
      prenom: "Leila",
      email: "haddad.leila@etudiant.ma",
      place: "A8",
      present: false,
    },
    {
      id: 9,
      matricule: "ETU-2023-009",
      nom: "Idrissi",
      prenom: "Omar",
      email: "idrissi.omar@etudiant.ma",
      place: "A9",
      present: true,
    },
    {
      id: 10,
      matricule: "ETU-2023-010",
      nom: "Jamal",
      prenom: "Amina",
      email: "jamal.amina@etudiant.ma",
      place: "A10",
      present: true,
    },
  ],
}

export default function SurveillantDashboard() {
  const { toast } = useToast()
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)
  const [surveillant, setSurveillant] = useState<any>(null)
  const [salleInfo, setSalleInfo] = useState<any>(null)
  const [examenInfo, setExamenInfo] = useState<any>(null)
  const [etudiants, setEtudiants] = useState<any[]>([])
  const [filteredEtudiants, setFilteredEtudiants] = useState<any[]>([])
  const [searchTerm, setSearchTerm] = useState("")

  // Simuler le chargement des données
  useEffect(() => {
    const timer = setTimeout(() => {
      setSurveillant(MOCK_DATA.surveillant)
      setSalleInfo(MOCK_DATA.salleInfo)
      setExamenInfo(MOCK_DATA.examenInfo)
      setEtudiants(MOCK_DATA.etudiants)
      setFilteredEtudiants(MOCK_DATA.etudiants)
      setLoading(false)
    }, 1000) // Simuler un délai de chargement de 1 seconde

    return () => clearTimeout(timer)
  }, [])

  // Filtrer les étudiants en fonction du terme de recherche
  useEffect(() => {
    if (!etudiants.length) return

    if (searchTerm.trim() === "") {
      setFilteredEtudiants(etudiants)
    } else {
      const filtered = etudiants.filter(
        (etudiant) =>
          etudiant.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
          etudiant.prenom.toLowerCase().includes(searchTerm.toLowerCase()) ||
          etudiant.matricule.toLowerCase().includes(searchTerm.toLowerCase()),
      )
      setFilteredEtudiants(filtered)
    }
  }, [searchTerm, etudiants])

  // Mettre à jour la présence d'un étudiant
  const togglePresence = (etudiantId: number, present: boolean) => {
    setEtudiants((prevEtudiants) =>
      prevEtudiants.map((etudiant) => {
        if (etudiant.id === etudiantId) {
          return { ...etudiant, present }
        }
        return etudiant
      }),
    )

    setFilteredEtudiants((prevEtudiants) =>
      prevEtudiants.map((etudiant) => {
        if (etudiant.id === etudiantId) {
          return { ...etudiant, present }
        }
        return etudiant
      }),
    )
  }

  // Simuler la sauvegarde des présences
  const savePresences = async () => {
    setSaving(true)

    // Simuler un délai de traitement
    setTimeout(() => {
      setSaving(false)

      // Afficher un message de succès
      toast({
        title: "Succès",
        description: "Les présences ont été enregistrées avec succès",
      })
    }, 1500)
  }

  // Simuler la génération d'un rapport
  const generatePresenceReport = () => {
    toast({
      title: "Génération du rapport",
      description: "Le rapport de présence a été généré et téléchargé",
    })
  }

  if (loading) {
    return (
      <div className="container mx-auto py-10">
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-gray-900"></div>
        </div>
      </div>
    )
  }

  return (
    <div className="container mx-auto py-6">
      <h1 className="text-2xl font-bold mb-6">Tableau de bord du surveillant</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg flex items-center">
              <User className="mr-2 h-5 w-5" />
              Surveillant
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p className="font-medium">
              {surveillant?.nom} {surveillant?.prenom}
            </p>
            <p className="text-sm text-gray-500">Matricule: {surveillant?.matricule}</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg flex items-center">
              <School className="mr-2 h-5 w-5" />
              Salle
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p className="font-medium">{salleInfo?.nom}</p>
            <p className="text-sm text-gray-500">Capacité: {salleInfo?.capacite} places</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg flex items-center">
              <BookOpen className="mr-2 h-5 w-5" />
              Examen
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p className="font-medium">{examenInfo?.matiere?.nom}</p>
            <div className="flex items-center text-sm text-gray-500">
              <Clock className="mr-1 h-4 w-4" />
              <span>
                {examenInfo?.heureDebut} - {examenInfo?.heureFin}
              </span>
            </div>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <CardTitle>Liste des étudiants</CardTitle>
            <div className="relative w-full md:w-64">
              <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-500" />
              <Input
                type="search"
                placeholder="Rechercher un étudiant..."
                className="pl-8"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
          </div>
        </CardHeader>
        <CardContent>
          {filteredEtudiants.length === 0 ? (
            <div className="text-center py-4">Aucun étudiant trouvé</div>
          ) : (
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead className="w-12">#</TableHead>
                    <TableHead>Matricule</TableHead>
                    <TableHead>Nom</TableHead>
                    <TableHead>Prénom</TableHead>
                    <TableHead>Place</TableHead>
                    <TableHead className="text-center">Présence</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {filteredEtudiants.map((etudiant, index) => (
                    <TableRow key={etudiant.id}>
                      <TableCell>{index + 1}</TableCell>
                      <TableCell className="font-medium">{etudiant.matricule}</TableCell>
                      <TableCell>{etudiant.nom}</TableCell>
                      <TableCell>{etudiant.prenom}</TableCell>
                      <TableCell>{etudiant.place || "-"}</TableCell>
                      <TableCell className="text-center">
                        <div className="flex items-center justify-center space-x-2">
                          <Checkbox
                            id={`presence-${etudiant.id}`}
                            checked={etudiant.present || false}
                            onCheckedChange={(checked) => togglePresence(etudiant.id, !!checked)}
                          />
                          <Label htmlFor={`presence-${etudiant.id}`} className="text-sm">
                            {etudiant.present ? "Présent" : "Absent"}
                          </Label>
                        </div>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          )}
        </CardContent>
        <CardFooter className="flex justify-between">
          <Button variant="outline" onClick={generatePresenceReport} className="flex items-center gap-2">
            <FileText className="h-4 w-4" />
            Générer rapport
          </Button>
          <Button onClick={savePresences} disabled={saving} className="flex items-center gap-2">
            {saving ? (
              <div className="animate-spin h-4 w-4 border-2 border-current border-t-transparent rounded-full"></div>
            ) : (
              <Save className="h-4 w-4" />
            )}
            Enregistrer les présences
          </Button>
        </CardFooter>
      </Card>
    </div>
  )
}
