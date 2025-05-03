"use client"

import { useEffect, useState } from "react"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { specialiteService } from "@/services/api"

interface SpecialiteSelectorProps {
  onSpecialiteChange: (specialiteId: string) => void
  value?: string
}

export default function SpecialiteSelector({ onSpecialiteChange, value }: SpecialiteSelectorProps) {
  const [specialites, setSpecialites] = useState<any[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchSpecialites = async () => {
      try {
        setLoading(true)
        const data = await specialiteService.getAll()
        setSpecialites(data)
        setError(null)
      } catch (err) {
        console.error("Erreur lors de la récupération des spécialités:", err)
        setError("Impossible de charger les spécialités")
      } finally {
        setLoading(false)
      }
    }

    fetchSpecialites()
  }, [])

  if (loading) {
    return (
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">Spécialité</label>
        <Select disabled>
          <SelectTrigger>
            <SelectValue placeholder="Chargement des spécialités..." />
          </SelectTrigger>
        </Select>
      </div>
    )
  }

  if (error) {
    return (
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">Spécialité</label>
        <div className="text-red-500 text-sm">{error}</div>
      </div>
    )
  }

  return (
    <div>
      <label htmlFor="specialite" className="block text-sm font-medium text-gray-700 mb-1">
        Spécialité
      </label>
      <Select value={value} onValueChange={onSpecialiteChange}>
        <SelectTrigger>
          <SelectValue placeholder="Sélectionner une spécialité" />
        </SelectTrigger>
        <SelectContent>
          {specialites.map((specialite) => (
            <SelectItem key={specialite.id} value={specialite.id.toString()}>
              {specialite.nom}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  )
}
