"use client"

import { useEffect, useState } from "react"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { niveauService } from "@/services/api"

interface NiveauSelectorProps {
  specialiteId?: string
  onNiveauChange: (niveauId: string) => void
  value?: string
}

export default function NiveauSelector({ specialiteId, onNiveauChange, value }: NiveauSelectorProps) {
  const [niveaux, setNiveaux] = useState<any[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchNiveaux = async () => {
      if (!specialiteId) {
        setNiveaux([])
        return
      }

      try {
        setLoading(true)
        const data = await niveauService.getBySpecialite(Number.parseInt(specialiteId))
        setNiveaux(data)
        setError(null)
      } catch (err) {
        console.error("Erreur lors de la récupération des niveaux:", err)
        setError("Impossible de charger les niveaux")
      } finally {
        setLoading(false)
      }
    }

    fetchNiveaux()
  }, [specialiteId])

  return (
    <div>
      <label htmlFor="niveau" className="block text-sm font-medium text-gray-700 mb-1">
        Niveau
      </label>
      <Select value={value} onValueChange={onNiveauChange} disabled={!specialiteId || loading}>
        <SelectTrigger>
          <SelectValue placeholder={loading ? "Chargement des niveaux..." : "Sélectionner un niveau"} />
        </SelectTrigger>
        <SelectContent>
          {niveaux.map((niveau) => (
            <SelectItem key={niveau.id} value={niveau.id.toString()}>
              {niveau.nom}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
      {error && <div className="text-red-500 text-sm mt-1">{error}</div>}
    </div>
  )
}
