using Bunifu.UI.WinForms;
using Bunifu.UI.WinForms.BunifuButton;
using Bunifu.UI.WinForms.Renderers.Snackbar;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace EduSafe
{
    internal class Support
    {
        public static void msi(string text)
        {
            MessageBox.Show(text, "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        public static void msw(string text)
        {
            MessageBox.Show(text, "Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning);
        }

        public static void mse(string text)
        {
            MessageBox.Show(text, "Eror", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }

        public static void clear(Control control)
        {
            foreach (var c in control.Controls)
            {
                if (c is BunifuTextBox)
                {
                    ((BunifuTextBox)c).Text = string.Empty;
                }

                if (c is ComboBox)
                {
                    ((ComboBox)c).SelectedIndex = -1;
                }
            }
        }

        public static void enable(Control control)
        {
            foreach (var c in control.Controls)
            {
                if (c is BunifuTextBox)
                {
                    ((BunifuTextBox)c).Enabled = false;
                }

                if (c is BunifuTextBox)
                {
                    ((BunifuTextBox)c).Enabled = true;
                }

                if (c is BunifuPanel)
                {
                    ((BunifuPanel)c).Enabled = false;
                }

                if (c is BunifuPanel)
                {
                    ((BunifuPanel)c).Enabled = true;
                }

                if (c is Bunifu.UI.WinForms.BunifuButton.BunifuButton)
                {
                    ((Bunifu.UI.WinForms.BunifuButton.BunifuButton)(c)).Enabled = false;
                }

                if (c is Bunifu.UI.WinForms.BunifuButton.BunifuButton)
                {
                    ((Bunifu.UI.WinForms.BunifuButton.BunifuButton)(c)).Enabled = true;
                }

                if (c is BunifuGroupBox)
                {
                    ((BunifuGroupBox)c).Enabled = false;
                }

                if (c is BunifuGroupBox)
                {
                    ((BunifuGroupBox)c).Enabled = true;
                }
            }
        }
    }
}
