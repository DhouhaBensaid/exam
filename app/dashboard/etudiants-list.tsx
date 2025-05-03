"use client"

import { useEffect, useState } from "react"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Input } from "@/components/ui/input"
import { etudiantService } from "@/services/api"
import { Search } from "lucide-react"

interface EtudiantsListProps {
  examenId?: string
  salleId?: string
}

export default function EtudiantsList({ examenId, salleId }: EtudiantsListProps) {
  const [etudiants, setEtudiants] = useState<any[]>([])
  const [filteredEtudiants, setFilteredEtudiants] = useState<any[]>([])
  const [searchTerm, setSearchTerm] = useState("")
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchEtudiants = async () => {
      if (!examenId || !salleId) {
        setEtudiants([])
        setFilteredEtudiants([])
        return
      }

      try {
        setLoading(true)
        const data = await etudiantService.getByExamenAndSalle(Number.parseInt(examenId), Number.parseInt(salleId))
        setEtudiants(data)
        setFilteredEtudiants(data)
        setError(null)
      } catch (err) {
        console.error("Erreur lors de la récupération des étudiants:", err)
        setError("Impossible de charger les étudiants")
      } finally {
        setLoading(false)
      }
    }

    fetchEtudiants()
  }, [examenId, salleId])

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

  if (loading) {
    return <div className="text-center py-4">Chargement des étudiants...</div>
  }

  if (error) {
    return <div className="text-red-500 text-center py-4">{error}</div>
  }

  if (!examenId || !salleId) {
    return <div className="text-center py-4">Veuillez sélectionner un examen et une salle pour voir les étudiants</div>
  }

  return (
    <div>
      <div className="relative w-full mb-4">
        <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-500" />
        <Input
          type="search"
          placeholder="Rechercher un étudiant..."
          className="pl-8"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {filteredEtudiants.length > 0 ? (
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
            {filteredEtudiants.map((etudiant) => (
              <TableRow key={etudiant.id}>
                <TableCell className="font-medium">{etudiant.matricule}</TableCell>
                <TableCell>{etudiant.nom}</TableCell>
                <TableCell>{etudiant.prenom}</TableCell>
                <TableCell>
                  {etudiant.affectations.find(
                    (a: any) => a.examen.id.toString() === examenId && a.salle.id.toString() === salleId,
                  )?.place || "-"}
                </TableCell>
                <TableCell>
                  {etudiant.affectations.find(
                    (a: any) => a.examen.id.toString() === examenId && a.salle.id.toString() === salleId,
                  )?.present
                    ? "Présent"
                    : "Absent"}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      ) : (
        <div className="text-center py-4">Aucun étudiant trouvé</div>
      )}
    </div>
  )
}
