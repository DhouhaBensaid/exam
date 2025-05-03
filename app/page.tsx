import { Button } from "@/components/ui/button"
import Image from "next/image"
import Link from "next/link"

export default function WelcomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-cyan-100 flex flex-col items-center justify-center p-4">
      <div className="w-full max-w-4xl bg-white rounded-xl shadow-2xl overflow-hidden flex flex-col items-center">
        <div className="w-full py-8 px-6 flex flex-col items-center">
          <h1 className="text-5xl md:text-6xl font-bold text-blue-600 tracking-wider text-center font-display mb-4">
            DIGITAL EXAM MANAGER
          </h1>
          <p className="text-gray-600 text-xl text-center max-w-2xl mb-8">
            Plateforme de gestion des examens pour les établissements d'enseignement
          </p>

          <div className="relative w-full h-64 md:h-80 mb-8">
            <Image
              src="https://www.medianet.tn/assets/public/images/jpg/MEDIANET/ms3.jpg"
              alt="Digital Exam Manager"
              fill
              style={{ objectFit: "cover" }}
              className="rounded-lg"
              priority
            />
          </div>

          <div className="flex flex-col items-center gap-4">
            <Link href="/login">
              <Button
                size="lg"
                className="text-lg px-8 py-6 bg-blue-600 hover:bg-blue-700 transition-all duration-300 transform hover:scale-105"
              >
                Commencer
              </Button>
            </Link>
            <p className="text-gray-500 text-sm mt-2">Connectez-vous pour accéder à votre espace personnel</p>
          </div>
        </div>

        <div className="w-full bg-blue-600 py-4 px-6 text-white text-center">
          <p>© 2024 Digital Exam Manager - Tous droits réservés</p>
        </div>
      </div>
    </div>
  )
}
