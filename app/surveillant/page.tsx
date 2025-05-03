"use client"

import { useState, useEffect } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Checkbox } from "@/components/ui/checkbox"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Search, Download, Save, Clock, Calendar, User } from "lucide-react"
import { format } from "date-fns"
import { toast } from "@/hooks/use-toast"
import api from "@/services/api"

export default function SurveillantPage() {
  const [examens, setExamens] = useState<any[]>([])
  const [selectedExamen, setSelectedExamen] = useState<string>("")
  const [salles, setSalles] = useState<any[]>([])
  const [selectedSalle, setSelectedSalle] = useState<string>("")
  const [etudiants, setEtudiants] = useState<any[]>([])
  const [filteredEtudiants, setFilteredEtudiants] = useState<any[]>([])
  const [searchTerm, setSearchTerm] = useState("")
  const [loading, setLoading] = useState(false)
  const [savingPresence, setSavingPresence] = useState(false)
  const [currentDate, setCurrentDate] = useState<string>(format(new Date(), "yyyy-MM-dd"))

  // Charger les examens du jour
  useEffect(() => {
    const fetchExamensJour = async () => {
      try {
        setLoading(true)
        const response = await api.get(`/api/examens/date/${currentDate}`)
        setExamens(response.data)
      } catch (error) {
        console.error("Erreur lors du chargement des examens:", error)
        toast({
          title: "Erreur",
          description: "Impossible de charger les examens du jour",
          variant: "destructive",
        })
      } finally {
        setLoading(false)
      }
    }

    fetchExamensJour()
  }, [currentDate])

  // Charger les salles lorsqu'un examen est sélectionné
  useEffect(() => {
    const fetchSalles = async () => {
      if (!selectedExamen) {
        setSalles([])
        return
      }

      try {
        setLoading(true)
        const response = await api.get(`/api/examens/${selectedExamen}/salles`)
        setSalles(response.data)
      } catch (error) {
        console.error("Erreur lors du chargement des salles:", error)
        toast({
          title: "Erreur",
          description: "Impossible de charger les salles pour cet examen",
          variant: "destructive",
        })
      } finally {
        setLoading(false)
      }
    }

    fetchSalles()
  }, [selectedExamen])

  // Charger les étudiants lorsqu'une salle est sélectionnée
  useEffect(() => {
    const fetchEtudiants = async () => {
      if (!selectedExamen || !selectedSalle) {
        setEtudiants([])
        setFilteredEtudiants([])
        return
      }

      try {
        setLoading(true)
        const response = await api.get(`/api/etudiants/examen/${selectedExamen}/salle/${selectedSalle}`)
        setEtudiants(response.data)
        setFilteredEtudiants(response.data)
      } catch (error) {
        console.error("Erreur lors du chargement des étudiants:", error)
        toast({
          title: "Erreur",
          description: "Impossible de charger les étudiants pour cette salle",
          variant: "destructive",
        })
      } finally {
        setLoading(false)
      }
    }

    fetchEtudiants()
  }, [selectedExamen, selectedSalle])

  // Filtrer les étudiants en fonction du terme de recherche
  useEffect(() => {
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
          return {
            ...etudiant,
            affectations: etudiant.affectations.map((a: any) => {
              if (a.examen.id.toString() === selectedExamen && a.salle.id.toString() === selectedSalle) {
                return { ...a, present }
              }
              return a
            }),
          }
        }
        return etudiant
      }),
    )

    setFilteredEtudiants((prevEtudiants) =>
      prevEtudiants.map((etudiant) => {
        if (etudiant.id === etudiantId) {
          return {
            ...etudiant,
            affectations: etudiant.affectations.map((a: any) => {
              if (a.examen.id.toString() === selectedExamen && a.salle.id.toString() === selectedSalle) {
                return { ...a, present }
              }
              return a
            }),
          }
        }
        return etudiant
      }),
    )
  }

  // Sauvegarder les présences
  const savePresences = async () => {
    if (!selectedExamen || !selectedSalle) {
      toast({
        title: "Erreur",
        description: "Veuillez sélectionner un examen et une salle",
        variant: "destructive",
      })
      return
    }

    try {
      setSavingPresence(true)

      // Préparer les données à envoyer
      const presenceData = etudiants.map((etudiant) => {
        const affectation = etudiant.affectations.find(
          (a: any) => a.examen.id.toString() === selectedExamen && a.salle.id.toString() === selectedSalle,
        )
        return {
          etudiantId: etudiant.id,
          examenId: Number.parseInt(selectedExamen),
          salleId: Number.parseInt(selectedSalle),
          present: affectation?.present || false,
        }
      })

      // Envoyer les données
      await api.post("/api/presences/save-batch", presenceData)

      toast({
        title: "Succès",
        description: "Les présences ont été enregistrées avec succès",
        variant: "success",
      })
    } catch (error) {
      console.error("Erreur lors de l'enregistrement des présences:", error)
      toast({
        title: "Erreur",
        description: "Impossible d'enregistrer les présences",
        variant: "destructive",
      })
    } finally {
      setSavingPresence(false)
    }
  }

  // Télécharger la liste d'émargement
  const downloadListeEmargement = () => {
    if (!selectedExamen || !selectedSalle) {
      toast({
        title: "Erreur",
        description: "Veuillez sélectionner un examen et une salle",
        variant: "destructive",
      })
      return
    }

    window.open(
      `${process.env.NEXT_PUBLIC_API_URL}/api/impression/liste-emargement/${selectedExamen}/${selectedSalle}`,
      "_blank",
    )
  }

  return (
    <div className="container mx-auto py-6">
      <h1 className="text-2xl font-bold mb-6">Gestion des Surveillances</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg flex items-center">
              <Calendar className="mr-2 h-5 w-5" />
              Date
            </CardTitle>
          </CardHeader>
          <CardContent>
            <Input
              type="date"
              value={currentDate}
              onChange={(e) => setCurrentDate(e.target.value)}
              className="w-full"
            />
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg flex items-center">
              <Clock className="mr-2 h-5 w-5" />
              Examen
            </CardTitle>
          </CardHeader>
          <CardContent>
            <Select value={selectedExamen} onValueChange={setSelectedExamen}>
              <SelectTrigger>
                <SelectValue placeholder="Sélectionner un examen" />
              </SelectTrigger>
              <SelectContent>
                {examens.map((examen) => (
                  <SelectItem key={examen.id} value={examen.id.toString()}>
                    {examen.matiere.nom} - {examen.heureDebut.substring(0, 5)} à {examen.heureFin.substring(0, 5)}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg flex items-center">
              <User className="mr-2 h-5 w-5" />
              Salle
            </CardTitle>
          </CardHeader>
          <CardContent>
            <Select value={selectedSalle} onValueChange={setSelectedSalle} disabled={!selectedExamen}>
              <SelectTrigger>
                <SelectValue placeholder="Sélectionner une salle" />
              </SelectTrigger>
              <SelectContent>
                {salles.map((salle) => (
                  <SelectItem key={salle.id} value={salle.id.toString()}>
                    {salle.nom} (Capacité: {salle.capacite})
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <CardTitle>Liste des Étudiants</CardTitle>
            <div className="flex flex-col md:flex-row gap-2">
              <Button
                variant="outline"
                onClick={downloadListeEmargement}
                disabled={!selectedExamen || !selectedSalle}
                className="flex items-center gap-2"
              >
                <Download className="h-4 w-4" />
                Liste d'émargement
              </Button>
              <Button
                onClick={savePresences}
                disabled={!selectedExamen || !selectedSalle || savingPresence}
                className="flex items-center gap-2"
              >
                {savingPresence ? (
                  <div className="animate-spin h-4 w-4 border-2 border-current border-t-transparent rounded-full"></div>
                ) : (
                  <Save className="h-4 w-4" />
                )}
                Enregistrer les présences
              </Button>
            </div>
          </div>
          <div className="relative w-full">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-500" />
            <Input
              type="search"
              placeholder="Rechercher un étudiant..."
              className="pl-8"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              disabled={!selectedExamen || !selectedSalle}
            />
          </div>
        </CardHeader>
        <CardContent>
          {loading ? (
            <div className="text-center py-4">Chargement...</div>
          ) : !selectedExamen || !selectedSalle ? (
            <div className="text-center py-4">Veuillez sélectionner un examen et une salle</div>
          ) : filteredEtudiants.length === 0 ? (
            <div className="text-center py-4">Aucun étudiant trouvé</div>
          ) : (
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Matricule</TableHead>
                    <TableHead>Nom</TableHead>
                    <TableHead>Prénom</TableHead>
                    <TableHead>Place</TableHead>
                    <TableHead>Présence</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {filteredEtudiants.map((etudiant) => {
                    const affectation = etudiant.affectations.find(
                      (a: any) => a.examen.id.toString() === selectedExamen && a.salle.id.toString() === selectedSalle,
                    )
                    return (
                      <TableRow key={etudiant.id}>
                        <TableCell className="font-medium">{etudiant.matricule}</TableCell>
                        <TableCell>{etudiant.nom}</TableCell>
                        <TableCell>{etudiant.prenom}</TableCell>
                        <TableCell>{affectation?.place || "-"}</TableCell>
                        <TableCell>
                          <div className="flex items-center space-x-2">
                            <Checkbox
                              id={`presence-${etudiant.id}`}
                              checked={affectation?.present || false}
                              onCheckedChange={(checked) => togglePresence(etudiant.id, !!checked)}
                            />
                            <Label htmlFor={`presence-${etudiant.id}`}>
                              {affectation?.present ? "Présent" : "Absent"}
                            </Label>
                          </div>
                        </TableCell>
                      </TableRow>
                    )
                  })}
                </TableBody>
              </Table>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}
