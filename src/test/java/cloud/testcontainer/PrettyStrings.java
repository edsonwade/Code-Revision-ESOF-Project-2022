package cloud.testcontainer;

@SuppressWarnings("all")
public class PrettyStrings {

    public static String logo = "\n" +
            "████████╗███████╗███████╗████████╗ ██████╗ ██████╗ ███╗   ██╗████████╗ █████╗ ██╗███╗   ██╗███████╗██████╗ ███████╗ \n" +
            "╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝██╔══██╗██║████╗  ██║██╔════╝██╔══██╗██╔════╝ \n" +
            "   ██║   █████╗  ███████╗   ██║   ██║     ██║   ██║██╔██╗ ██║   ██║   ███████║██║██╔██╗ ██║█████╗  ██████╔╝███████╗ \n" +
            "   ██║   ██╔══╝  ╚════██║   ██║   ██║     ██║   ██║██║╚██╗██║   ██║   ██╔══██║██║██║╚██╗██║██╔══╝  ██╔══██╗╚════██║ \n" +
            "   ██║   ███████╗███████║   ██║   ╚██████╗╚██████╔╝██║ ╚████║   ██║   ██║  ██║██║██║ ╚████║███████╗██║  ██║███████║ \n" +
            "   ╚═╝   ╚══════╝╚══════╝   ╚═╝    ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝╚══════╝ \n" +
            "  \n" +
            "  Congratulations on running your first test! 🎉\n" +
            "  Runtime used: \n" +
            "      ::::::\n" +
            " \n" +
            "  You can now return to the website to complete your onboarding.\n" +
            " \n" +
            "";

    public static String ohNo = "" +
            " ██████╗ ██╗  ██╗    ███╗   ██╗ ██████╗               ██╗\n" +
            "██╔═══██╗██║  ██║    ████╗  ██║██╔═══██╗    ██╗      ██╔╝\n" +
            "██║   ██║███████║    ██╔██╗ ██║██║   ██║    ╚═╝█████╗██║ \n" +
            "██║   ██║██╔══██║    ██║╚██╗██║██║   ██║    ██╗╚════╝██║ \n" +
            "╚██████╔╝██║  ██║    ██║ ╚████║╚██████╔╝    ╚═╝      ╚██╗\n" +
            " ╚═════╝ ╚═╝  ╚═╝    ╚═╝  ╚═══╝ ╚═════╝               ╚═╝\n" +
            "                                                         ";

    public static String getLogo(String runtimeName) {
        return logo.replaceAll("::::::", runtimeName);
    }
}
