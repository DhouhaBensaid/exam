
"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Check, User, Lock, UserPlus } from "lucide-react"
import { useRouter } from "next/navigation"
import { authService } from "../src/services/api"
import { toast } from "@/hooks/use-toast"
import { setToken, setUserRole } from "@/lib/auth"

export default function LoginForm() {
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")
  const [rememberMe, setRememberMe] = useState(false)
  const [isLoading, setIsLoading] = useState(false)
  const router = useRouter()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)

    console.log("Tentative de connexion avec:", { username, password, rememberMe })

    try {
      console.log("Envoi de la requête à authService...")
      const response = await authService.login(username, password)

      console.log("Réponse complète du serveur:", response)
      console.log("Données de la réponse:", response.data)

      if (!response?.token) {
        console.error("Erreur: Token manquant dans la réponse", response)
        throw new Error("Token manquant dans la réponse")
      }

      console.log("Token reçu:", response.token.substring(0, 10) + "...")
      console.log("Rôle utilisateur:", response.user?.role)

      setToken(response.token, rememberMe)
      setUserRole(response.user.role)

      console.log("Token stocké avec succès")

      // ✅ Redirection après succès
      router.push("/dashboard")

    } catch (error: any) {
      console.error("Détails de l'erreur:", {
        name: error.name,
        message: error.message,
        responseData: error.response?.data,
        status: error.response?.status,
        config: error.config,
        stack: error.stack,
      })

      const errorMessage = error.response?.data?.message || error.message || "Identifiants incorrects"

      toast({
        title: "Erreur de connexion",
        description: errorMessage,
        variant: "destructive",
      })
    } finally {
      setIsLoading(false)
      console.log("Fin du processus de connexion")
    }
  }

  return (
      <div className="flex flex-col">
        <h2 className="text-xl font-bold text-black border-b-2 border-gray-200 pb-2 mb-6">
          ESPACE DE CONNEXION
        </h2>

        <form onSubmit={handleSubmit} className="w-full space-y-6">
          <div className="space-y-2">
            <Label htmlFor="username" className="text-base font-medium">
              Nom d&apos;utilisateur
            </Label>
            <div className="relative">
              <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <User className="h-4 w-4 text-gray-400" />
              </div>
              <Input
                  id="username"
                  type="text"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  className="pl-10 border-gray-300"
                  placeholder="Entrez votre nom d'utilisateur"
                  required
              />
            </div>
          </div>

          <div className="space-y-2">
            <Label htmlFor="password" className="text-base font-medium">
              Mot de passe
            </Label>
            <div className="relative">
              <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <Lock className="h-4 w-4 text-gray-400" />
              </div>
              <Input
                  id="password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="pl-10 border-gray-300"
                  placeholder="Entrez votre mot de passe"
                  required
              />
            </div>
          </div>

          <div className="flex items-center space-x-2">
            <input
                type="checkbox"
                id="remember"
                checked={rememberMe}
                onChange={(e) => setRememberMe(e.target.checked)}
                className="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-500"
            />
            <Label htmlFor="remember" className="text-sm text-gray-600">
              Se souvenir de moi
            </Label>
          </div>

          <div className="flex justify-between pt-2">
            <Button
                type="submit"
                disabled={isLoading}
                className="bg-green-600 hover:bg-green-700 text-white px-6 py-2 rounded-md flex items-center gap-2"
            >
              {isLoading ? (
                  <div className="animate-spin h-4 w-4 border-2 border-white border-t-transparent rounded-full"></div>
              ) : (
                  <Check className="h-4 w-4" />
              )}
              Se connecter
            </Button>

            <Button
                type="button"
                variant="outline"
                className="border-blue-500 text-blue-600 hover:bg-blue-50 px-6 py-2 rounded-md flex items-center gap-2"
                onClick={() => router.push("/register")}
            >
              <UserPlus className="h-4 w-4" />
              S&apos;enregistrer
            </Button>
          </div>

          <div className="pt-4 text-center">
            <a href="/forgot-password" className="text-sm text-blue-600 hover:underline">
              Mot de passe oublié?
            </a>
          </div>
        </form>
      </div>
  )
}
