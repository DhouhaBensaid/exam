import { NextResponse } from "next/server"
import type { NextRequest } from "next/server"

export function middleware(request: NextRequest) {
  // Vérifier si l'utilisateur est connecté
  const token = request.cookies.get("token")?.value
  const isLoggedIn = !!token

  // Liste des chemins publics qui ne nécessitent pas d'authentification
  const publicPaths = ["/login", "/register", "/forgot-password"]
  const isPublicPath = publicPaths.some((path) => request.nextUrl.pathname.startsWith(path))

  // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté et essaie d'accéder à une page protégée
  if (!isLoggedIn && !isPublicPath) {
    const url = new URL("/login", request.url)
    url.searchParams.set("from", request.nextUrl.pathname)
    return NextResponse.redirect(url)
  }

  // Rediriger vers le tableau de bord si l'utilisateur est déjà connecté et essaie d'accéder à une page publique
  if (isLoggedIn && isPublicPath) {
    return NextResponse.redirect(new URL("/dashboard", request.url))
  }

  return NextResponse.next()
}

// Configurer les chemins sur lesquels le middleware doit s'exécuter
export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico|images).*)"],
}
