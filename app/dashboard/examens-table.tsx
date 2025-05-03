"use client"

import { useEffect, useState } from "react"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { examenService } from "@/services/api"
import { format } from "date-fns"
import { fr } from "date-fns/locale"

interface ExamensTableProps {
  niveauId?: string
  anneeAcademique: string
}

export default function ExamensTable({ niveauId, anneeAcademique }: ExamensTableProps) {
  const [examens, setExamens] = useState<any[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchExamens = async () => {
      if (!niveauId) {
        setExamens([])
        return
      }

      try {
        setLoading(true)
        const data = await examenService.getByNiveau(Number.parseInt(niveauId), anneeAcademique)
        setExamens(data)
        setError(null)
      } catch (err) {
        console.error("Erreur lors de la récupération des examens:", err)
        setError("Impossible de charger les examens")
      } finally {
        setLoading(false)
      }
    }

    fetchExamens()
  }, [niveauId, anneeAcademique])

  if (loading) {
    return <div className="text-center py-4">Chargement des examens...</div>
  }

  if (error) {
    return <div className="text-red-500 text-center py-4">{error}</div>
  }

  if (!niveauId) {
    return <div className="text-center py-4">Veuillez sélectionner un niveau pour voir l'emploi du temps</div>
  }

  if (examens.length === 0) {
    return <div className="text-center py-4">Aucun examen trouvé pour ce niveau</div>
  }

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Matière</TableHead>
          <TableHead>Date</TableHead>
          <TableHead>Heure</TableHead>
          <TableHead>Salle</TableHead>
          <TableHead>Type</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {examens.map((examen) => (
          <TableRow key={examen.id}>
            <TableCell className="font-medium">{examen.matiere.nom}</TableCell>
            <TableCell>{format(new Date(examen.dateExamen), "dd MMMM yyyy", { locale: fr })}</TableCell>
            <TableCell>{`${examen.heureDebut.substring(0, 5)} - ${examen.heureFin.substring(0, 5)}`}</TableCell>
            <TableCell>{examen.examenSalles.map((es: any) => es.salle.nom).join(", ")}</TableCell>
            <TableCell>
              {examen.typeExamen === "FINAL" ? "Final" : examen.typeExamen === "PARTIEL" ? "Partiel" : "Rattrapage"}
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  )
}
