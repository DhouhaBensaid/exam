package com.gestionexamens.service;

import com.gestionexamens.repository.PresenceRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ImpressionService {

    private final PresenceService presenceService;
    private final PresenceRepository presenceRepository;

    @Autowired
    public ImpressionService(PresenceService presenceService, PresenceRepository presenceRepository) {
        this.presenceService = presenceService;
        this.presenceRepository = presenceRepository;
    }

    public byte[] genererListePresences(Long examenId, Long salleId) throws DocumentException {
        List<Map<String, Object>> presences = presenceService.findPresencesByExamenAndSalle(examenId, salleId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);

        document.open();

        // Titre
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("Liste de Présence", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Date
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
        Paragraph date = new Paragraph("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        document.add(date);

        document.add(Chunk.NEWLINE);

        // Tableau des présences
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        // En-têtes
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        table.addCell(new PdfPCell(new Phrase("Matricule", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Nom", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Prénom", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Place", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Présent", headerFont)));

        // Données
        for (Map<String, Object> presence : presences) {
            table.addCell(presence.get("matricule").toString());
            table.addCell(presence.get("nom").toString());
            table.addCell(presence.get("prenom").toString());
            table.addCell(presence.get("place") != null ? presence.get("place").toString() : "");
            table.addCell((Boolean) presence.get("present") ? "Oui" : "Non");
        }

        document.add(table);
        document.add(Chunk.NEWLINE);

        // Signature
        Paragraph signature = new Paragraph("Signature du surveillant: ____________________", normalFont);
        signature.setAlignment(Element.ALIGN_RIGHT);
        document.add(signature);

        document.close();
        return baos.toByteArray();
    }

    public byte[] genererRapportAbsences(Long salleId) throws DocumentException {
        List<Map<String, Object>> absences = presenceService.findAbsencesBySalle(salleId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);

        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("Rapport d'Absences", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
        Paragraph date = new Paragraph("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        document.add(date);

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        table.addCell(new PdfPCell(new Phrase("Matricule", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Nom", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Prénom", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Matière", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Date", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Horaire", headerFont)));

        for (Map<String, Object> absence : absences) {
            table.addCell(absence.get("matricule").toString());
            table.addCell(absence.get("nom").toString());
            table.addCell(absence.get("prenom").toString());
            table.addCell(absence.get("matiereNom").toString());
            table.addCell(absence.get("dateExamen").toString());
            table.addCell(absence.get("heureDebut") + " - " + absence.get("heureFin"));
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }

    public byte[] genererListeEmargement(Long examenId, Long salleId) throws DocumentException {
        List<Map<String, Object>> etudiants = presenceRepository.findEtudiantsPourEmargement(examenId, salleId);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("Liste d'Émargement", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.addCell("Matricule");
        table.addCell("Nom");
        table.addCell("Prénom");
        table.addCell("Signature");
        table.addCell("Présence");

        for (Map<String, Object> etudiant : etudiants) {
            table.addCell(etudiant.get("matricule").toString());
            table.addCell(etudiant.get("nom").toString());
            table.addCell(etudiant.get("prenom").toString());
            table.addCell("");
            table.addCell("");
        }

        document.add(table);
        document.close();

        return baos.toByteArray();
    }
}
