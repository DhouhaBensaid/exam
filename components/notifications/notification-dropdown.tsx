"use client"

import { useState, useEffect } from "react"
import { Bell } from "lucide-react"
import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Badge } from "@/components/ui/badge"
import { ScrollArea } from "@/components/ui/scroll-area"
import { format } from "date-fns"
import { fr } from "date-fns/locale"
import api from "@/services/api"

interface Notification {
  id: number
  titre: string
  message: string
  dateCreation: string
  lu: boolean
  type: string
}

export default function NotificationDropdown() {
  const [notifications, setNotifications] = useState<Notification[]>([])
  const [unreadCount, setUnreadCount] = useState(0)
  const [loading, setLoading] = useState(false)

  const fetchNotifications = async () => {
    try {
      setLoading(true)
      const response = await api.get("/api/notifications")
      setNotifications(response.data)

      // Mettre à jour le compteur de notifications non lues
      const countResponse = await api.get("/api/notifications/count-unread")
      setUnreadCount(countResponse.data.count)
    } catch (error) {
      console.error("Erreur lors du chargement des notifications:", error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchNotifications()

    // Rafraîchir les notifications toutes les minutes
    const interval = setInterval(fetchNotifications, 60000)
    return () => clearInterval(interval)
  }, [])

  const markAsRead = async (notificationId: number) => {
    try {
      await api.post(`/api/notifications/${notificationId}/mark-as-read`)

      // Mettre à jour l'état local
      setNotifications((prevNotifications) =>
        prevNotifications.map((notification) =>
          notification.id === notificationId ? { ...notification, lu: true } : notification,
        ),
      )

      // Mettre à jour le compteur
      setUnreadCount((prevCount) => Math.max(0, prevCount - 1))
    } catch (error) {
      console.error("Erreur lors du marquage de la notification comme lue:", error)
    }
  }

  const markAllAsRead = async () => {
    try {
      await api.post("/api/notifications/mark-all-as-read")

      // Mettre à jour l'état local
      setNotifications((prevNotifications) => prevNotifications.map((notification) => ({ ...notification, lu: true })))

      // Mettre à jour le compteur
      setUnreadCount(0)
    } catch (error) {
      console.error("Erreur lors du marquage de toutes les notifications comme lues:", error)
    }
  }

  const getNotificationIcon = (type: string) => {
    switch (type) {
      case "CHANGEMENT_HORAIRE":
        return "🕒"
      case "CHANGEMENT_SALLE":
        return "🏢"
      case "ANNULATION":
        return "❌"
      case "ALERTE":
        return "⚠️"
      default:
        return "ℹ️"
    }
  }

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="ghost" size="icon" className="relative">
          <Bell className="h-5 w-5" />
          {unreadCount > 0 && (
            <Badge
              className="absolute -top-1 -right-1 px-1.5 py-0.5 min-w-[1.25rem] h-5 flex items-center justify-center"
              variant="destructive"
            >
              {unreadCount > 99 ? "99+" : unreadCount}
            </Badge>
          )}
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end" className="w-80">
        <DropdownMenuLabel className="flex justify-between items-center">
          <span>Notifications</span>
          {unreadCount > 0 && (
            <Button variant="ghost" size="sm" onClick={markAllAsRead} className="text-xs h-7">
              Tout marquer comme lu
            </Button>
          )}
        </DropdownMenuLabel>
        <DropdownMenuSeparator />
        <ScrollArea className="h-80">
          {loading ? (
            <div className="p-4 text-center text-sm text-gray-500">Chargement...</div>
          ) : notifications.length === 0 ? (
            <div className="p-4 text-center text-sm text-gray-500">Aucune notification</div>
          ) : (
            notifications.map((notification) => (
              <DropdownMenuItem
                key={notification.id}
                className={`flex flex-col items-start p-3 cursor-pointer ${
                  !notification.lu ? "bg-gray-50 dark:bg-gray-800" : ""
                }`}
                onClick={() => markAsRead(notification.id)}
              >
                <div className="flex items-center w-full">
                  <span className="mr-2 text-lg">{getNotificationIcon(notification.type)}</span>
                  <span className="font-medium flex-1 truncate">{notification.titre}</span>
                  {!notification.lu && (
                    <Badge variant="secondary" className="ml-2 px-1.5 py-0.5 h-5">
                      Nouveau
                    </Badge>
                  )}
                </div>
                <p className="text-sm text-gray-500 mt-1 w-full line-clamp-2">{notification.message}</p>
                <p className="text-xs text-gray-400 mt-1 w-full text-right">
                  {format(new Date(notification.dateCreation), "dd MMM yyyy à HH:mm", { locale: fr })}
                </p>
              </DropdownMenuItem>
            ))
          )}
        </ScrollArea>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}
