import type React from "react"
import type { Metadata } from "next"

export const metadata: Metadata = {
  title: "Gestion des Examens - Espace Surveillant",
  description: "Tableau de bord pour les surveillants d'examens",
}

export default function SurveillantLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-white shadow-sm">
        <div className="container mx-auto py-4 px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center">
            <h1 className="text-xl font-semibold text-gray-900">Espace Surveillant</h1>
            <a href="/login" className="text-sm text-gray-600 hover:text-gray-900">
              Déconnexion
            </a>
          </div>
        </div>
      </header>
      <main>{children}</main>
    </div>
  )
}
